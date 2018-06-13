package controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebView;

public class TemarioController {
    public Label textoNormal;
    public HTMLEditor htmlEditor;
    public WebView webView;
    public Button boton;

    public void ponerTexto(ActionEvent actionEvent) {
        String texto = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\"><html><head><META http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"></head><body>\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "<div>\n" +
                "<div><p> </p><h1><a href=\"https://www.google.com<a name=\"0.1_header-n6\"></a>1. Introducción</h1><hr><p> </p><div><p><span><a href=\"#0.1_header-n6\">1. Introducción</a></span><span><a href=\"#0.1_header-n12\">1.1 Conceptos Básicos</a></span><span><a href=\"#0.1_header-n13\">Computadora</a></span><span><a href=\"#0.1_header-n16\">Informática</a></span><span><a href=\"#0.1_header-n19\">H<wbr>ardware</a></span><span><a href=\"#0.1_header-n22\">Software</a></span><span><a href=\"#0.1_header-n25\">Sistema Operativo</a></span><span><a href=\"#0.1_header-n28\">Algoritmo</a></span><span><a href=\"#0.1_header-n31\">Programa Informático</a></span><span><a href=\"#0.1_header-n34\">Aplicación Informática</a></span><span><a href=\"#0.1_header-n38\">1.2 Codificación de la información</a></span><span><a href=\"#0.1_header-n41\">Sistemas Numéricos</a></span><span><a href=\"#0.1_header-n58\">Sistemas posicionales</a></span></p></div><hr><h2><a name=\"0.1_header-n12\"></a>1.1 Conceptos Básicos</h2><h3><a name=\"0.1_header-n13\"></a>Computadora</h3><p>Una <strong>computadora</strong>, o como se la conoce popularmente, un <strong>ordenador</strong>, es una máquina electrónica, analógica o digital, dotada de una memoria de gran capacidad y de métodos de tratamiento de la información, capaz de resolver problemas matemáticos y lógicos mediante la utilización automática de programas informáticos.</p><h3><a name=\"0.1_header-n16\"></a>Informática</h3><p>Conjunto de conocimientos científicos y técnicas que hacen posible el tratamiento automático de la información por medio de ordenadores.</p><h3><a name=\"0.1_header-n19\"></a>Hardware</h3><p>Componentes físicos que forman parte de un ordenador (o de otro dispositivo electrónico): procesador, RAM, impresora, teclado, ratón,...</p><h3><a name=\"0.1_header-n22\"></a>Software</h3><p>Es el conjunto de los programas de cómputo, procedimientos, reglas, documentación y datos asociados, que forman parte de las operaciones de  un sistema de computación.</p><h3><a name=\"0.1_header-n25\"></a>Sistema Operativo</h3><p>Se trata del software encargado de gestionar el ordenador. Es la aplicación que oculta la física real del ordenador para mostrarnos una interfaz que permita al usuario un mejor y más fácil manejo de la computadora. Por ejemplo, : Windows, Linux, MacOS.</p><h3><a name=\"0.1_header-n28\"></a>Algoritmo</h3><p>Conjunto ordenado y finito de operaciones que permite hallar la solución de un problema.</p><h3><a name=\"0.1_header-n31\"></a>Programa Informático</h3><p>Es una secuencia de instrucciones escritas para realizar una tarea específica en una computadora.</p><h3><a name=\"0.1_header-n34\"></a>Aplicación Informática</h3><p>Software formado por uno o más programas, la documentación de los mismos y los archivos necesarios para su\n" +
                "funcionamiento, de modo que el conjunto completo forma una herramienta de trabajo en un ordenador.</p><h2><a name=\"0.1_header-n38\"></a>1.2 Codificación de la información</h2><p>Un ordenador maneja información de todo tipo. Nuestra perspectiva humana nos permite rápidamente diferenciar lo que son números, de lo que es texto, imagen,...Sin embargo al tratarse de una máquina digital, el ordenador sólo es capaz de representar números en forma binaria. Por ello todos los ordenadores necesitan codificar la información del mundo real al equivalente binario entendible por el ordenador.</p><h3><a name=\"0.1_header-n41\"></a>Sistemas Numéricos</h3><p>Existen dos tipos de sistemas numéricos:</p><ol start=\"\"><li><p><strong>Sistemas no posicionales</strong></p><p>En ellos se utilizan símbolos cuyo valor numérico es siempre el mismo independientemente de donde se sitúen.\n" +
                "Es lo que ocurre con la numeración romana. En esta numeración el símbolo I significa siempre uno independientemente de su posición.</p></li><li><p><strong>Sistemas posicionales</strong></p><p>En ellos los símbolos numéricos cambian de valor en función de la posición que ocupen. Es el caso de nuestra numeración, el símbolo 2, en la cifra 12 vale 2; mientras que en la cifra 21 vale veinte.</p></li></ol><p>La historia ha demostrado que los sistemas posicionales son mucho mejores para los cálculos matemáticos ya que las operaciones matemáticas son más sencillas. Todos los sistemas posicionales tienen una <strong>base</strong>, que es el número total de símbolos que utiliza el sistema. </p><h3><a name=\"0.1_header-n58\"></a>Sistemas posicionales</h3><ul><li>Sistema decimal: la base es 10 ya que utiliza 10 símbolos, desde el 0 hasta el 9. </li><li>Sistema binario: la base es 2, utiliza 0 y 1. </li><li>Sistema octal: la base es 8, desde el 0 hasta el 7. </li><li>Sistema hexadecimal</li></ul><p> </p></div>\n" +
                "</div>\n" +
                "</body></html>";
        textoNormal.setText(texto);
        webView.getEngine().loadContent(texto);
        System.out.print(texto);
    }
}
