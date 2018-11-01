package controllers;

import com.sun.javafx.scene.text.TextLayout;
import com.sun.javafx.tk.Toolkit;
import controllers.alumno.ListItem_AsignacionDiaController;
import controllers.alumno.ListItem_TemaController;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.OverrunStyle;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import models.*;

import java.io.IOException;
import java.text.Bidi;
import java.util.ArrayList;
import java.util.List;

import static javafx.scene.control.OverrunStyle.*;

public class Utils {

    static final TextLayout layout = Toolkit.getToolkit().getTextLayoutFactory().createLayout();
    static final Text helper = new Text();
    static final double DEFAULT_WRAPPING_WIDTH = helper.getWrappingWidth();
    static final double DEFAULT_LINE_SPACING = helper.getLineSpacing();
    static final String DEFAULT_TEXT = helper.getText();
    static final TextBoundsType DEFAULT_BOUNDS_TYPE = helper.getBoundsType();

    public static boolean isLabelOverflow(Label label){
        return computeClippedWrappedText(label.getFont(), label.getText(), label.getPrefWidth(), label.getPrefHeight(), label.getTextOverrun(), label.getEllipsisString(), TextBoundsType.VISUAL);
    }

    static double computeTextWidth(Font font, String text, double wrappingWidth) {
        layout.setContent(text != null ? text : "", font.impl_getNativeFont());
        layout.setWrapWidth((float)wrappingWidth);
        return layout.getBounds().getWidth();
    }

    static double computeTextHeight(Font font, String text, double wrappingWidth, TextBoundsType boundsType) {
        return computeTextHeight(font, text, wrappingWidth, 0, boundsType);
    }

    @SuppressWarnings("deprecation")
    static double computeTextHeight(Font font, String text, double wrappingWidth, double lineSpacing, TextBoundsType boundsType) {
        layout.setContent(text != null ? text : "", font.impl_getNativeFont());
        layout.setWrapWidth((float)wrappingWidth);
        layout.setLineSpacing((float)lineSpacing);
        if (boundsType == TextBoundsType.LOGICAL_VERTICAL_CENTER) {
            layout.setBoundsType(TextLayout.BOUNDS_CENTER);
        } else {
            layout.setBoundsType(0);
        }
        return layout.getBounds().getHeight();
    }

    static int computeTruncationIndex(Font font, String text, double width) {
        helper.setText(text);
        helper.setFont(font);
        helper.setWrappingWidth(0);
        helper.setLineSpacing(0);
        // The -2 is a fudge to make sure the result more often matches
        // what we get from using computeTextWidth instead. It's not yet
        // clear what causes the small discrepancies.
        Bounds bounds = helper.getLayoutBounds();
        Point2D endPoint = new Point2D(width - 2, bounds.getMinY() + bounds.getHeight() / 2);
        final int index = helper.impl_hitTestChar(endPoint).getCharIndex();
        // RESTORE STATE
        helper.setWrappingWidth(DEFAULT_WRAPPING_WIDTH);
        helper.setLineSpacing(DEFAULT_LINE_SPACING);
        helper.setText(DEFAULT_TEXT);
        return index;
    }

    static boolean computeClippedText(Font font, String text, double width,
                                     OverrunStyle type, String ellipsisString) {
        if (font == null) {
            throw new IllegalArgumentException("Must specify a font");
        }
        OverrunStyle style = (type == null || type == CLIP) ? ELLIPSIS : type;
        final String ellipsis = (type == CLIP) ? "" : ellipsisString;
        // if the text is empty or null or no ellipsis, then it always fits
        if (text == null || "".equals(text)) {
            return false;
        }
        // if the string width is < the available width, then it fits and
        // doesn't need to be clipped.  We use a double point comparison
        // of 0.001 (1/1000th of a pixel) to account for any numerical
        // discrepancies introduced when the available width was calculated.
        // MenuItemSkinBase.doLayout, for example, does a number of double
        // point operations when computing the available width.
        final double stringWidth = computeTextWidth(font, text, 0);
        if (stringWidth - width < 0.0010F) {
            return false;
        }
        // the width used by the ellipsis string
        final double ellipsisWidth = computeTextWidth(font, ellipsis, 0);
        // the available maximum width to fit chars into. This is essentially
        // the width minus the space required for the ellipsis string
        final double availableWidth = width - ellipsisWidth;

        if (width < ellipsisWidth) {
            // The ellipsis doesn't fit.
            return true;
        }

        // if we got here, then we must clip the text with an ellipsis.
        // this can be pretty expensive depending on whether "complex" text
        // layout needs to be taken into account. So each ellipsis option has
        // to take into account two code paths: the easy way and the correct
        // way. This is flagged by the "complexLayout" boolean
        // TODO make sure this function call takes into account ligatures, kerning,
        // and such as that will change the layout characteristics of the text
        // and will require a full complex layout
        // TODO since we don't have all the stuff available in FX to determine
        // complex text, I'm going to for now assume complex text is always false.
        final boolean complexLayout = false;
        //requiresComplexLayout(font, text);

        // generally all we want to do is count characters and add their widths.
        // For ellipsis that breaks on words, we do NOT want to include any
        // hanging whitespace.
        if (style == ELLIPSIS ||
                style == WORD_ELLIPSIS ||
                style == LEADING_ELLIPSIS ||
                style == LEADING_WORD_ELLIPSIS) {

            final boolean wordTrim =
                    (style == WORD_ELLIPSIS || style == LEADING_WORD_ELLIPSIS);
            String substring;
            if (complexLayout) {
                //            AttributedString a = new AttributedString(text);
                //            LineBreakMeasurer m = new LineBreakMeasurer(a.getIterator(), frc);
                //            substring = text.substring(0, m.nextOffset((double)availableWidth));
            } else {
                // RT-23458: Use a faster algorithm for the most common case
                // where truncation happens at the end, i.e. for ELLIPSIS and
                // CLIP, but not for other cases such as WORD_ELLIPSIS, etc.
                if (style == ELLIPSIS && !new Bidi(text, Bidi.DIRECTION_LEFT_TO_RIGHT).isMixed()) {
                    int hit = computeTruncationIndex(font, text, width - ellipsisWidth);
                    if (hit < 0 || hit >= text.length()) {
                        return false;
                    } else {
                        return true;
                    }
                }

                // simply total up the widths of all chars to determine how many
                // will fit in the available space. Remember the last whitespace
                // encountered so that if we're breaking on words we can trim
                // and omit it.
                double total = 0.0F;
                int whitespaceIndex = -1;
                // at the termination of the loop, index will be one past the
                // end of the substring
                int index = 0;
                int start = (style == LEADING_ELLIPSIS || style == LEADING_WORD_ELLIPSIS) ? (text.length() - 1) : (0);
                int end = (start == 0) ? (text.length() - 1) : 0;
                int stepValue = (start == 0) ? 1 : -1;
                boolean done = (start == 0) ? start > end : start < end;
                for (int i = start; !done ; i += stepValue) {
                    index = i;
                    char c = text.charAt(index);
                    total = computeTextWidth(font,
                            (start == 0) ? text.substring(0, i + 1)
                                    : text.substring(i, start + 1),
                            0);
                    if (Character.isWhitespace(c)) {
                        whitespaceIndex = index;
                    }
                    if (total > availableWidth) {
                        break;
                    }
                    done = start == 0? i >= end : i <= end;
                }
                final boolean fullTrim = !wordTrim || whitespaceIndex == -1;
                substring = (start == 0) ?
                        (text.substring(0, fullTrim ? index : whitespaceIndex)) :
                        (text.substring((fullTrim ? index : whitespaceIndex) + 1));
                assert(!text.equals(substring));
            }
            if (style == ELLIPSIS || style == WORD_ELLIPSIS) {
                return true;
            } else {
                //style is LEADING_ELLIPSIS or LEADING_WORD_ELLIPSIS
                return true;
            }
        } else {
            // these two indexes are INCLUSIVE not exclusive
            int leadingIndex = 0;
            int trailingIndex = 0;
            int leadingWhitespace = -1;
            int trailingWhitespace = -1;
            // The complex case is going to be killer. What I have to do is
            // read all the chars from the left up to the leadingIndex,
            // and all the chars from the right up to the trailingIndex,
            // and sum those together to get my total. That is, I cannot have
            // a running total but must retotal the cummulative chars each time
            if (complexLayout) {
            } else /*            double leadingTotal = 0;
               double trailingTotal = 0;
               for (int i=0; i<text.length(); i++) {
               double total = computeStringWidth(metrics, text.substring(0, i));
               if (total + trailingTotal > availableWidth) break;
               leadingIndex = i;
               leadingTotal = total;
               if (Character.isWhitespace(text.charAt(i))) leadingWhitespace = leadingIndex;

               int index = text.length() - (i + 1);
               total = computeStringWidth(metrics, text.substring(index - 1));
               if (total + leadingTotal > availableWidth) break;
               trailingIndex = index;
               trailingTotal = total;
               if (Character.isWhitespace(text.charAt(index))) trailingWhitespace = trailingIndex;
               }*/
            {
                // either CENTER_ELLIPSIS or CENTER_WORD_ELLIPSIS
                // for this case I read one char on the left, then one on the end
                // then second on the left, then second from the end, etc until
                // I have used up all the availableWidth. At that point, I trim
                // the string twice: once from the start to firstIndex, and
                // once from secondIndex to the end. I then insert the ellipsis
                // between the two.
                leadingIndex = -1;
                trailingIndex = -1;
                double total = 0.0F;
                for (int i = 0; i <= text.length() - 1; i++) {
                    char c = text.charAt(i);
                    //total += metrics.charWidth(c);
                    total += computeTextWidth(font, "" + c, 0);
                    if (total > availableWidth) {
                        break;
                    }
                    leadingIndex = i;
                    if (Character.isWhitespace(c)) {
                        leadingWhitespace = leadingIndex;
                    }
                    int index = text.length() - 1 - i;
                    c = text.charAt(index);
                    //total += metrics.charWidth(c);
                    total += computeTextWidth(font, "" + c, 0);
                    if (total > availableWidth) {
                        break;
                    }
                    trailingIndex = index;
                    if (Character.isWhitespace(c)) {
                        trailingWhitespace = trailingIndex;
                    }
                }
            }
            if (leadingIndex < 0) {
                return true;
            }
            if (style == CENTER_ELLIPSIS) {
                if (trailingIndex < 0) {
                    return true;
                }
                return true;
            } else {
                boolean leadingIndexIsLastLetterInWord =
                        Character.isWhitespace(text.charAt(leadingIndex + 1));
                int index = (leadingWhitespace == -1 || leadingIndexIsLastLetterInWord) ? (leadingIndex + 1) : (leadingWhitespace);
                String leading = text.substring(0, index);
                if (trailingIndex < 0) {
                    return true;
                }
                boolean trailingIndexIsFirstLetterInWord =
                        Character.isWhitespace(text.charAt(trailingIndex - 1));
                index = (trailingWhitespace == -1 || trailingIndexIsFirstLetterInWord) ? (trailingIndex) : (trailingWhitespace + 1);
                String trailing = text.substring(index);
                return true;
            }
        }
    }

    static boolean computeClippedWrappedText(Font font, String text, double width,
                                                           double height, OverrunStyle truncationStyle,
                                                           String ellipsisString, TextBoundsType boundsType) {

        boolean isClipped = false;

        if (font == null) {
            throw new IllegalArgumentException("Must specify a font");
        }

        String ellipsis = (truncationStyle == CLIP) ? "" : ellipsisString;
        int eLen = ellipsis.length();
        // Do this before using helper, as it's not reentrant.
        double eWidth = computeTextWidth(font, ellipsis, 0);
        double eHeight = computeTextHeight(font, ellipsis, 0, boundsType);

        if (width < eWidth || height < eHeight) {
            // The ellipsis doesn't fit.
            return false; // RT-30868 - return text, not empty string.
        }

        helper.setText(text);
        helper.setFont(font);
        helper.setWrappingWidth((int)Math.ceil(width));
        helper.setBoundsType(boundsType);
        helper.setLineSpacing(0);

        boolean leading =  (truncationStyle == LEADING_ELLIPSIS ||
                truncationStyle == LEADING_WORD_ELLIPSIS);
        boolean center =   (truncationStyle == CENTER_ELLIPSIS ||
                truncationStyle == CENTER_WORD_ELLIPSIS);
        boolean trailing = !(leading || center);
        boolean wordTrim = (truncationStyle == WORD_ELLIPSIS ||
                truncationStyle == LEADING_WORD_ELLIPSIS ||
                truncationStyle == CENTER_WORD_ELLIPSIS);

        String result = text;
        int len = (result != null) ? result.length() : 0;
        int centerLen = -1;

        Point2D centerPoint = null;
        if (center) {
            // Find index of character in the middle of the visual text area
            centerPoint = new Point2D((width - eWidth) / 2, height / 2 - helper.getBaselineOffset());
        }

        // Find index of character at the bottom left of the text area.
        // This should be the first character of a line that would be clipped.
        Point2D endPoint = new Point2D(0, height - helper.getBaselineOffset());

        int hit = helper.impl_hitTestChar(endPoint).getCharIndex();
        if (hit >= len) {
            helper.setBoundsType(TextBoundsType.LOGICAL); // restore
            return false;
        }
        if (center) {
            hit = helper.impl_hitTestChar(centerPoint).getCharIndex();
        }

        if (hit > 0 && hit < len) {
            // Step one, make a truncation estimate.

            if (center || trailing) {
                int ind = hit;
                if (center) {
                    // This is for the first part, i.e. beginning of text up to ellipsis.
                    if (wordTrim) {
                        int brInd = lastBreakCharIndex(text, ind + 1);
                        if (brInd >= 0) {
                            ind = brInd + 1;
                        } else {
                            brInd = firstBreakCharIndex(text, ind);
                            if (brInd >= 0) {
                                ind = brInd + 1;
                            }
                        }
                    }
                    centerLen = ind + eLen;
                } // else: text node wraps at words, so wordTrim is not needed here.
                result = result.substring(0, ind) + ellipsis;
                isClipped = true;
            }

            if (leading || center) {
                // The hit is an index counted from the beginning, but we need
                // the opposite, i.e. an index counted from the end.  However,
                // the Text node does not support wrapped line layout in the
                // reverse direction, starting at the bottom right corner.

                // We'll simulate by assuming the index will be a similar
                // number, then back up 10 characters just to add some slop.
                // For example, the ending lines might pack tighter than the
                // beginning lines, and therefore fit a higher number of
                // characters.
                int ind = Math.max(0, len - hit - 10);
                if (ind > 0 && wordTrim) {
                    int brInd = lastBreakCharIndex(text, ind + 1);
                    if (brInd >= 0) {
                        ind = brInd + 1;
                    } else {
                        brInd = firstBreakCharIndex(text, ind);
                        if (brInd >= 0) {
                            ind = brInd + 1;
                        }
                    }
                }
                if (center) {
                    // This is for the second part, i.e. from ellipsis to end of text.
                    result = result + text.substring(ind);
                } else {
                    result = ellipsis + text.substring(ind);
                    isClipped = true;
                }
            }

            // Step two, check if text still overflows after we added the ellipsis.
            // If so, remove one char or word at a time.
            while (true) {
                helper.setText(result);
                int hit2 = helper.impl_hitTestChar(endPoint).getCharIndex();
                if (center && hit2 < centerLen) {
                    // No room for text after ellipsis. Maybe there is a newline
                    // here, and the next line falls outside the view.
                    if (hit2 > 0 && result.charAt(hit2-1) == '\n') {
                        hit2--;
                    }
                    result = text.substring(0, hit2) + ellipsis;
                    isClipped = true;
                    break;
                } else if (hit2 > 0 && hit2 < result.length()) {
                    if (leading) {
                        int ind = eLen + 1; // Past ellipsis and first char.
                        if (wordTrim) {
                            int brInd = firstBreakCharIndex(result, ind);
                            if (brInd >= 0) {
                                ind = brInd + 1;
                            }
                        }
                        result = ellipsis + result.substring(ind);
                        isClipped = true;
                    } else if (center) {
                        int ind = centerLen + 1; // Past ellipsis and first char.
                        if (wordTrim) {
                            int brInd = firstBreakCharIndex(result, ind);
                            if (brInd >= 0) {
                                ind = brInd + 1;
                            }
                        }
                        result = result.substring(0, centerLen) + result.substring(ind);
                    } else {
                        int ind = result.length() - eLen - 1; // Before last char and ellipsis.
                        if (wordTrim) {
                            int brInd = lastBreakCharIndex(result, ind);
                            if (brInd >= 0) {
                                ind = brInd;
                            }
                        }
                        result = result.substring(0, ind) + ellipsis;
                        isClipped = true;
                    }
                } else {
                    break;
                }
            }
        }
        // RESTORE STATE
        helper.setWrappingWidth(DEFAULT_WRAPPING_WIDTH);
        helper.setLineSpacing(DEFAULT_LINE_SPACING);
        helper.setText(DEFAULT_TEXT);
        helper.setBoundsType(DEFAULT_BOUNDS_TYPE);
        return isClipped;
    }

    private static int firstBreakCharIndex(String str, int start) {
        char[] chars = str.toCharArray();
        for (int i = start; i < chars.length; i++) {
            if (isPreferredBreakCharacter(chars[i])) {
                return i;
            }
        }
        return -1;
    }

    private static int lastBreakCharIndex(String str, int start) {
        char[] chars = str.toCharArray();
        for (int i = start; i >= 0; i--) {
            if (isPreferredBreakCharacter(chars[i])) {
                return i;
            }
        }
        return -1;
    }

    private static boolean isPreferredBreakCharacter(char ch) {
        if (Character.isWhitespace(ch)) {
            return true;
        } else {
            switch (ch) {
                case ';' :
                case ':' :
                case '.' :
                    return true;
                default: return false;
            }
        }
    }

    public static void setAsignacionesInVBox(List<AsignacionAlumnoLista> asignaciones, VBox internVbox, CallbackAsignacion internCallback, Class internClass) {
        ArrayList<Parent> asignacionesItemList = new ArrayList<>();
        double sumarioHeight = 0;

        for (AsignacionAlumnoLista asignacionAlumnoLista : asignaciones) {
            FXMLLoader loader = new FXMLLoader(internClass.getResource("/fxml/item/listItem_asignacionDia.fxml"));
            Parent node = null;
            try {
                node = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            ListItem_AsignacionDiaController controller = loader.getController();
            controller.setAsignacion(asignacionAlumnoLista);
            node.setOnMouseEntered(event -> internVbox.getScene().setCursor(Cursor.HAND));
            node.setOnMouseExited(event -> internVbox.getScene().setCursor(Cursor.DEFAULT));
            node.setOnMouseClicked(event -> internCallback.setCenterAsignacion(asignacionAlumnoLista.getCodigoAsignacion()));

            asignacionesItemList.add(node);
            sumarioHeight += controller.getHeight() + 20;
        }

        internVbox.getChildren().setAll(asignacionesItemList);

        for (Parent node : asignacionesItemList) {
            VBox.setMargin(node, new Insets(10, 10, 10, 10));
        }

        internVbox.setPrefHeight(sumarioHeight);
    }

    public static void setAsignacionesEvaluarInVBox(List<AsignacionAlumnoLista> asignaciones, VBox internVbox, CallbackAsignacionEvaluar internCallback, Class internClass) {
        ArrayList<Parent> asignacionesItemList = new ArrayList<>();
        double sumarioHeight = 0;

        for (AsignacionAlumnoLista asignacionAlumnoLista : asignaciones) {
            FXMLLoader loader = new FXMLLoader(internClass.getResource("/fxml/item/listItem_asignacionDia.fxml"));
            Parent node = null;
            try {
                node = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            ListItem_AsignacionDiaController controller = loader.getController();
            controller.setAsignacion(asignacionAlumnoLista);
            node.setOnMouseEntered(event -> internVbox.getScene().setCursor(Cursor.HAND));
            node.setOnMouseExited(event -> internVbox.getScene().setCursor(Cursor.DEFAULT));
            node.setOnMouseClicked(event -> {
                if (asignacionAlumnoLista.isEntregado()){
                    internCallback.setCenterAsignacionEvaluar(asignacionAlumnoLista.getCodigoAsignacion());
                }
            });

            asignacionesItemList.add(node);
            sumarioHeight += controller.getHeight() + 20;
        }

        internVbox.getChildren().setAll(asignacionesItemList);

        for (Parent node : asignacionesItemList) {
            VBox.setMargin(node, new Insets(10, 10, 10, 10));
        }

        internVbox.setPrefHeight(sumarioHeight);
    }

    public static void setTemasInVBox(List<ItemListTema> temas, VBox internVbox, CallbackTema internCallback, Class internClass) {
        ArrayList<Parent> temasItemList = new ArrayList<>();
        double sumarioHeight = 0;

        for (ItemListTema itemListTema : temas) {
            FXMLLoader loader = new FXMLLoader(internClass.getResource("/fxml/item/listItem_tema.fxml"));
            Parent node = null;
            try {
                node = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            ListItem_TemaController controller = loader.getController();
            controller.setTema(itemListTema);
            node.setOnMouseEntered(event -> internVbox.getScene().setCursor(Cursor.HAND));
            node.setOnMouseExited(event -> internVbox.getScene().setCursor(Cursor.DEFAULT));
            node.setOnMouseClicked(event -> internCallback.setCenterTema(itemListTema.getCodigoAsignatura(), itemListTema.getCodigoTema()));

            temasItemList.add(node);
            // Altura del elemento + margen
            sumarioHeight += 70 + 20;
        }

        internVbox.getChildren().setAll(temasItemList);

        for (Parent node : temasItemList) {
            VBox.setMargin(node, new Insets(10, 10, 10, 10));
        }

        internVbox.setPrefHeight(sumarioHeight);
    }

    public static class CellAsignatura extends ListCell<ItemListAsignatura> {
        private Parent itemRoot;
        private Label lbl_nombre;
        private EventHandler<MouseEvent> callback;

        public CellAsignatura(EventHandler<MouseEvent> clickCallback) {
            callback = clickCallback;
        }

        @Override
        protected void updateItem(ItemListAsignatura itemListAsignatura, boolean empty) {
            super.updateItem(itemListAsignatura, empty);
            if (itemListAsignatura == null) {
                setText(null);
                setGraphic(null);
                return;
            }
            if (null == itemRoot) {
                try {
                    itemRoot = FXMLLoader.load(getClass().getResource(("/fxml/item/listItem_asignatura.fxml")));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                lbl_nombre = (Label) itemRoot.lookup("#lbl_nombre");
                itemRoot.setOnMouseClicked(callback);
            }
            itemRoot.setUserData(itemListAsignatura.getCodigoAsignatura());
            itemRoot.setOnMouseEntered(event -> itemRoot.getScene().setCursor(Cursor.HAND));
            itemRoot.setOnMouseExited(event -> itemRoot.getScene().setCursor(Cursor.DEFAULT));
            lbl_nombre.setText(itemListAsignatura.getNombreAsignatura());
            setGraphic(itemRoot);
        }
    }
}
