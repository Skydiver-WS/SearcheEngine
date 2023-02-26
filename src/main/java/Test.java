import lombok.SneakyThrows;
import org.apache.lucene.morphology.LuceneMorphology;
import org.apache.lucene.morphology.russian.RussianLuceneMorphology;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Safelist;
import searchengine.services.lemma.LemmaAnalyze;

import java.util.List;
import java.util.Map;

public class Test {
    @SneakyThrows
    public static void main(String[] args) {
//        LemmaAnalyze lemmaAnalyze = new LemmaAnalyze();
//        String text = "Данный код  (две возможные исходные\n" +
//                "формы слова «леса»):\n" +
//                "лес\n" +
//                "леса\n" +
//                "● Создайте класс с методом, который будет принимать в качестве\n" +
//                "параметра текст и возвращать перечень лемм для каждого слова в этом\n" +
//                "тексте (за исключением междометий, союзов, предлогов и частиц — см.\n" +
//                "ниже) и количество упоминаний каждой такой леммы в переданном\n" +
//                "тексте. Ниже описаны детали реализации данного метода.\n" +
//                "Пример текста на входе:\n" +
//                "Повторное появление леопарда в Осетии позволяет предположить,\n" +
//                "что леопард постоянно обитает в некоторых районах Северного\n" +
//                "Кавказа. ОБРАТИТЕ ВНИМАНИЕ! Разделение текстов на слова вы уже\n" +
//                "реализовывали в одном из заданий к модулю «Строки» курса\n" +
//                "21«Java-разработчик c нуля». Рекомендуем использовать в этом проекте\n" +
//                "уже написанный вами ранее код.\n" +
//                "● Метод должен возвращать HashMap<String, Integer>, в котором ключами\n" +
//                "будут леммы, а значениями — их количества в переданном тексте.\n" +
//                "● Передаваемые в метод тексты необходимо очищать от служебных частей\n" +
//                "речи. Часть речи можно определить так: Такой код выдаст следующую информацию:\n" +
//                "или|n СОЮЗ\n" +
//                "В данном случае «СОЮЗ» означает, что слово является союзом. Другие\n" +
//                "примеры:\n" +
//                "и|o МЕЖД\n" +
//                "копал|A С мр,ед,им\n" +
//                "копать|a Г дст,прш,мр,ед\n" +
//                "хитро|j Н\n" +
//                "хитрый|Y КР_ПРИЛ ср,ед,од,но\n" +
//                "синий|Y П мр,ед,вн,но\n" +
//                "● Если с реализацией кода возникают сложности, посмотрите наш пример\n" +
//                "такого кода.\n" +
//                "● Также можете посмотреть пример реализации приложения с\n" +
//                "использованием данных классов и библиотек в проекте, который мы вам\n" +
//                "рекомендовали ранее. Можете загрузить его к себе на компьютер и\n" +
//                "собрать в соответствии с инструкциями, приведёнными в файле\n" +
//                "README.md (файл лежит в корне проекта).\n" +
//                "● В этом же классе реализуйте метод, который будет очищать код\n" +
//                "веб-страниц от HTML-тегов.\n" +
//                "● Реализуйте функцию индексации отдельной веб-страницы. Для этого\n" +
//                "сначала реализуйте метод контроллера для команды API indexPage в\n" +
//                "соответствии с технической спецификацией. <settings xmlns=\"http://maven.apache.org/SETTINGS/1.0.0\"\n" +
//                "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
//                "xsi:schemaLocation=\"http://maven.apache.org/SETTINGS/1.0.0\n" +
//                "https://maven.apache.org/xsd/settings-1.0.0.xsd\">\n" +
//                "<servers>\n" +
//                "<server>\n" +
//                "<id>skillbox-gitlab</id>\n" +
//                "<configuration>\n" +
//                "<httpHeaders>\n" +
//                "<property>\n" +
//                "<name>Private-Token</name>";
////       String text = "\"that and say that it was better before. It may be a little nostalgia;\\n\"";
//        Map<String, Integer> test = lemmaAnalyze.lemma(text);
//        for (String key:test.keySet()) {
//            System.out.println(key + " - " + test.get(key));
//        }

//        LuceneMorphology luceneMorph =
//          new RussianLuceneMorphology();
//        List<String> wordBaseForms =
//          luceneMorph.getMorphInfo("леса");
//        wordBaseForms.forEach(System.out::println);
//        System.out.println(wordBaseForms.size());

        String html = "\n" +
          "<!doctype html>\n" +
          "<html lang=\"en\">\n" +
          " <head>\n" +
          "  <title>Safelist (jsoup Java HTML Parser 1.15.4 API)</title>\n" +
          "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
          "  <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n" +
          "  <meta name=\"dc.created\" content=\"2023-02-18\">\n" +
          "  <meta name=\"description\" content=\"declaration: package: org.jsoup.safety, class: Safelist\">\n" +
          "  <meta name=\"generator\" content=\"javadoc/ClassWriterImpl\">\n" +
          "  <link rel=\"stylesheet\" type=\"text/css\" href=\"../../../stylesheet.css\" title=\"Style\">\n" +
          "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
          " </head>\n" +
          " <body class=\"class-declaration-page\">\n" +
          "  <div class=\"flex-box\">\n" +
          "   <header role=\"banner\" class=\"flex-header\">\n" +
          "    <nav role=\"navigation\"><!-- ========= START OF TOP NAVBAR ======= -->\n" +
          "     <div class=\"top-nav\" id=\"navbar-top\">\n" +
          "      <div class=\"skip-nav\">\n" +
          "       <a href=\"#skip-navbar-top\" title=\"Skip navigation links\">Skip navigation links</a>\n" +
          "      </div>\n" +
          "      <ul id=\"navbar-top-firstrow\" class=\"nav-list\" title=\"Navigation\">\n" +
          "       <li class=\"home\"><a href=\"//jsoup.org/\" title=\"jsoup, the HTML parser for Java\">jsoup</a></li>\n" +
          "       <li><a href=\"../../../index.html\">Overview</a></li>\n" +
          "       <li><a href=\"package-summary.html\">Package</a></li>\n" +
          "       <li class=\"nav-bar-cell1-rev\">Class</li>\n" +
          "       <li><a href=\"class-use/Safelist.html\">Use</a></li>\n" +
          "       <li><a href=\"package-tree.html\">Tree</a></li>\n" +
          "       <li><a href=\"../../../deprecated-list.html\">Deprecated</a></li>\n" +
          "       <li><a href=\"../../../index-all.html\">Index</a></li>\n" +
          "       <li><a href=\"../../../help-doc.html#class\">Help</a></li>\n" +
          "      </ul>\n" +
          "     </div> <!-- ========= END OF TOP NAVBAR ========= --> <span class=\"skip-nav\" id=\"skip-navbar-top\"></span>\n" +
          "    </nav>\n" +
          "   </header>\n" +
          "   <div class=\"flex-content\">\n" +
          "    <main role=\"main\"><!-- ======== START OF CLASS DATA ======== -->\n" +
          "     <div class=\"header\">\n" +
          "      <div class=\"sub-title\">\n" +
          "       <span class=\"package-label-in-type\">Package</span>&nbsp;<a href=\"package-summary.html\">org.jsoup.safety</a>\n" +
          "      </div>\n" +
          "      <h1 title=\"Class Safelist\" class=\"title\">Class Safelist</h1>\n" +
          "     </div>\n" +
          "     <div class=\"inheritance\" title=\"Inheritance Tree\">\n" +
          "      <a href=\"https://docs.oracle.com/javase/8/docs/api/java/lang/Object.html\" title=\"class or interface in java.lang\" class=\"external-link\">java.lang.Object</a>\n" +
          "      <div class=\"inheritance\">\n" +
          "       org.jsoup.safety.Safelist\n" +
          "      </div>\n" +
          "     </div>\n" +
          "     <section class=\"class-description\" id=\"class-description\">\n" +
          "      <hr>\n" +
          "      <div class=\"type-signature\">\n" +
          "       <span class=\"modifiers\">public class </span><span class=\"element-name type-name-label\">Safelist</span> <span class=\"extends-implements\">extends <a href=\"https://docs.oracle.com/javase/8/docs/api/java/lang/Object.html\" title=\"class or interface in java.lang\" class=\"external-link\">Object</a></span>\n" +
          "      </div>\n" +
          "      <div class=\"block\">\n" +
          "       Safe-lists define what HTML (elements and attributes) to allow through the cleaner. Everything else is removed. \n" +
          "       <p>Start with one of the defaults:</p>\n" +
          "       <ul>\n" +
          "        <li><a href=\"#none()\"><code>none()</code></a></li>\n" +
          "        <li><a href=\"#simpleText()\"><code>simpleText()</code></a></li>\n" +
          "        <li><a href=\"#basic()\"><code>basic()</code></a></li>\n" +
          "        <li><a href=\"#basicWithImages()\"><code>basicWithImages()</code></a></li>\n" +
          "        <li><a href=\"#relaxed()\"><code>relaxed()</code></a></li>\n" +
          "       </ul>\n" +
          "       <p>If you need to allow more through (please be careful!), tweak a base safelist with:</p>\n" +
          "       <ul>\n" +
          "        <li><a href=\"#addTags(java.lang.String...)\"><code>addTags(String...tagNames)</code></a></li>\n" +
          "        <li><a href=\"#addAttributes(java.lang.String,java.lang.String...)\"><code>addAttributes(String tagName, String...attributes)</code></a></li>\n" +
          "        <li><a href=\"#addEnforcedAttribute(java.lang.String,java.lang.String,java.lang.String)\"><code>addEnforcedAttribute(String tagName, String attribute, String value)</code></a></li>\n" +
          "        <li><a href=\"#addProtocols(java.lang.String,java.lang.String,java.lang.String...)\"><code>addProtocols(String tagName, String attribute, String...protocols)</code></a></li>\n" +
          "       </ul>\n" +
          "       <p>You can remove any setting from an existing safelist with:</p>\n" +
          "       <ul>\n" +
          "        <li><a href=\"#removeTags(java.lang.String...)\"><code>removeTags(String...tagNames)</code></a></li>\n" +
          "        <li><a href=\"#removeAttributes(java.lang.String,java.lang.String...)\"><code>removeAttributes(String tagName, String...attributes)</code></a></li>\n" +
          "        <li><a href=\"#removeEnforcedAttribute(java.lang.String,java.lang.String)\"><code>removeEnforcedAttribute(String tagName, String attribute)</code></a></li>\n" +
          "        <li><a href=\"#removeProtocols(java.lang.String,java.lang.String,java.lang.String...)\"><code>removeProtocols(String tagName, String attribute, String...removeProtocols)</code></a></li>\n" +
          "       </ul>\n" +
          "       <p>The cleaner and these safelists assume that you want to clean a <code>body</code> fragment of HTML (to add user supplied HTML into a templated page), and not to clean a full HTML document. If the latter is the case, either wrap the document HTML around the cleaned body HTML, or create a safelist that allows <code>html</code> and <code>head</code> elements as appropriate.</p>\n" +
          "       <p>If you are going to extend a safelist, please be very careful. Make sure you understand what attributes may lead to XSS attack vectors. URL attributes are particularly vulnerable and require careful validation. See the <a href=\"https://owasp.org/www-community/xss-filter-evasion-cheatsheet\">XSS Filter Evasion Cheat Sheet</a> for some XSS attack examples (that jsoup will safegaurd against the default Cleaner and Safelist configuration).</p>\n" +
          "      </div>\n" +
          "     </section>\n" +
          "     <section class=\"summary\">\n" +
          "      <ul class=\"summary-list\"><!-- ======== CONSTRUCTOR SUMMARY ======== -->\n" +
          "       <li>\n" +
          "        <section class=\"constructor-summary\" id=\"constructor-summary\">\n" +
          "         <h2>Constructor Summary</h2>\n" +
          "         <div class=\"caption\">\n" +
          "          <span>Constructors</span>\n" +
          "         </div>\n" +
          "         <div class=\"summary-table two-column-summary\">\n" +
          "          <div class=\"table-header col-first\">\n" +
          "           Constructor\n" +
          "          </div>\n" +
          "          <div class=\"table-header col-last\">\n" +
          "           Description\n" +
          "          </div>\n" +
          "          <div class=\"col-constructor-name even-row-color\">\n" +
          "           <code><a href=\"#%3Cinit%3E()\" class=\"member-name-link\">Safelist</a>()</code>\n" +
          "          </div>\n" +
          "          <div class=\"col-last even-row-color\">\n" +
          "           <div class=\"block\">\n" +
          "            Create a new, empty safelist.\n" +
          "           </div>\n" +
          "          </div>\n" +
          "          <div class=\"col-constructor-name odd-row-color\">\n" +
          "           <code><a href=\"#%3Cinit%3E(org.jsoup.safety.Safelist)\" class=\"member-name-link\">Safelist</a><wbr>(<a href=\"Safelist.html\" title=\"class in org.jsoup.safety\">Safelist</a>&nbsp;copy)</code>\n" +
          "          </div>\n" +
          "          <div class=\"col-last odd-row-color\">\n" +
          "           <div class=\"block\">\n" +
          "            Deep copy an existing Safelist to a new Safelist.\n" +
          "           </div>\n" +
          "          </div>\n" +
          "         </div>\n" +
          "        </section></li> <!-- ========== METHOD SUMMARY =========== -->\n" +
          "       <li>\n" +
          "        <section class=\"method-summary\" id=\"method-summary\">\n" +
          "         <h2>Method Summary</h2>\n" +
          "         <div id=\"method-summary-table\">\n" +
          "          <div id=\"method-summary-table.tabpanel\" role=\"tabpanel\">\n" +
          "           <div class=\"summary-table three-column-summary\" aria-labelledby=\"method-summary-table-tab0\">\n" +
          "            <div class=\"table-header col-first\">\n" +
          "             Modifier and Type\n" +
          "            </div>\n" +
          "            <div class=\"table-header col-second\">\n" +
          "             Method\n" +
          "            </div>\n" +
          "            <div class=\"table-header col-last\">\n" +
          "             Description\n" +
          "            </div>\n" +
          "            <div class=\"col-first even-row-color method-summary-table method-summary-table-tab2 method-summary-table-tab4\">\n" +
          "             <code><a href=\"Safelist.html\" title=\"class in org.jsoup.safety\">Safelist</a></code>\n" +
          "            </div>\n" +
          "            <div class=\"col-second even-row-color method-summary-table method-summary-table-tab2 method-summary-table-tab4\">\n" +
          "             <code><a href=\"#addAttributes(java.lang.String,java.lang.String...)\" class=\"member-name-link\">addAttributes</a><wbr>(<a href=\"https://docs.oracle.com/javase/8/docs/api/java/lang/String.html\" title=\"class or interface in java.lang\" class=\"external-link\">String</a>&nbsp;tag, <a href=\"https://docs.oracle.com/javase/8/docs/api/java/lang/String.html\" title=\"class or interface in java.lang\" class=\"external-link\">String</a>...&nbsp;attributes)</code>\n" +
          "            </div>\n" +
          "            <div class=\"col-last even-row-color method-summary-table method-summary-table-tab2 method-summary-table-tab4\">\n" +
          "             <div class=\"block\">\n" +
          "              Add a list of allowed attributes to a tag.\n" +
          "             </div>\n" +
          "            </div>\n" +
          "            <div class=\"col-first odd-row-color method-summary-table method-summary-table-tab2 method-summary-table-tab4\">\n" +
          "             <code><a href=\"Safelist.html\" title=\"class in org.jsoup.safety\">Safelist</a></code>\n" +
          "            </div>\n" +
          "            <div class=\"col-second odd-row-color method-summary-table method-summary-table-tab2 method-summary-table-tab4\">\n" +
          "             <code><a href=\"#addEnforcedAttribute(java.lang.String,java.lang.String,java.lang.String)\" class=\"member-name-link\">addEnforcedAttribute</a><wbr>(<a href=\"https://docs.oracle.com/javase/8/docs/api/java/lang/String.html\" title=\"class or interface in java.lang\" class=\"external-link\">String</a>&nbsp;tag, <a href=\"https://docs.oracle.com/javase/8/docs/api/java/lang/String.html\" title=\"class or interface in java.lang\" class=\"external-link\">String</a>&nbsp;attribute, <a href=\"https://docs.oracle.com/javase/8/docs/api/java/lang/String.html\" title=\"class or interface in java.lang\" class=\"external-link\">String</a>&nbsp;value)</code>\n" +
          "            </div>\n" +
          "            <div class=\"col-last odd-row-color method-summary-table method-summary-table-tab2 method-summary-table-tab4\">\n" +
          "             <div class=\"block\">\n" +
          "              Add an enforced attribute to a tag.\n" +
          "             </div>\n" +
          "            </div>\n" +
          "            <div class=\"col-first even-row-color method-summary-table method-summary-table-tab2 method-summary-table-tab4\">\n" +
          "             <code><a href=\"Safelist.html\" title=\"class in org.jsoup.safety\">Safelist</a></code>\n" +
          "            </div>\n" +
          "            <div class=\"col-second even-row-color method-summary-table method-summary-table-tab2 method-summary-table-tab4\">\n" +
          "             <code><a href=\"#addProtocols(java.lang.String,java.lang.String,java.lang.String...)\" class=\"member-name-link\">addProtocols</a><wbr>(<a href=\"https://docs.oracle.com/javase/8/docs/api/java/lang/String.html\" title=\"class or interface in java.lang\" class=\"external-link\">String</a>&nbsp;tag, <a href=\"https://docs.oracle.com/javase/8/docs/api/java/lang/String.html\" title=\"class or interface in java.lang\" class=\"external-link\">String</a>&nbsp;attribute, <a href=\"https://docs.oracle.com/javase/8/docs/api/java/lang/String.html\" title=\"class or interface in java.lang\" class=\"external-link\">String</a>...&nbsp;protocols)</code>\n" +
          "            </div>\n" +
          "            <div class=\"col-last even-row-color method-summary-table method-summary-table-tab2 method-summary-table-tab4\">\n" +
          "             <div class=\"block\">\n" +
          "              Add allowed URL protocols for an element's URL attribute.\n" +
          "             </div>\n" +
          "            </div>\n" +
          "            <div class=\"col-first odd-row-color method-summary-table method-summary-table-tab2 method-summary-table-tab4\">\n" +
          "             <code><a href=\"Safelist.html\" title=\"class in org.jsoup.safety\">Safelist</a></code>\n" +
          "            </div>\n" +
          "            <div class=\"col-second odd-row-color method-summary-table method-summary-table-tab2 method-summary-table-tab4\">\n" +
          "             <code><a href=\"#addTags(java.lang.String...)\" class=\"member-name-link\">addTags</a><wbr>(<a href=\"https://docs.oracle.com/javase/8/docs/api/java/lang/String.html\" title=\"class or interface in java.lang\" class=\"external-link\">String</a>...&nbsp;tags)</code>\n" +
          "            </div>\n" +
          "            <div class=\"col-last odd-row-color method-summary-table method-summary-table-tab2 method-summary-table-tab4\">\n" +
          "             <div class=\"block\">\n" +
          "              Add a list of allowed elements to a safelist.\n" +
          "             </div>\n" +
          "            </div>\n" +
          "            <div class=\"col-first even-row-color method-summary-table method-summary-table-tab1 method-summary-table-tab4\">\n" +
          "             <code>static <a href=\"Safelist.html\" title=\"class in org.jsoup.safety\">Safelist</a></code>\n" +
          "            </div>\n" +
          "            <div class=\"col-second even-row-color method-summary-table method-summary-table-tab1 method-summary-table-tab4\">\n" +
          "             <code><a href=\"#basic()\" class=\"member-name-link\">basic</a>()</code>\n" +
          "            </div>\n" +
          "            <div class=\"col-last even-row-color method-summary-table method-summary-table-tab1 method-summary-table-tab4\">\n" +
          "             <div class=\"block\">\n" +
          "              This safelist allows a fuller range of text nodes: <code>a, b, blockquote, br, cite, code, dd, dl, dt, em, i, li, ol, p, pre, q, small, span, strike, strong, sub, sup, u, ul</code>, and appropriate attributes.\n" +
          "             </div>\n" +
          "            </div>\n" +
          "            <div class=\"col-first odd-row-color method-summary-table method-summary-table-tab1 method-summary-table-tab4\">\n" +
          "             <code>static <a href=\"Safelist.html\" title=\"class in org.jsoup.safety\">Safelist</a></code>\n" +
          "            </div>\n" +
          "            <div class=\"col-second odd-row-color method-summary-table method-summary-table-tab1 method-summary-table-tab4\">\n" +
          "             <code><a href=\"#basicWithImages()\" class=\"member-name-link\">basicWithImages</a>()</code>\n" +
          "            </div>\n" +
          "            <div class=\"col-last odd-row-color method-summary-table method-summary-table-tab1 method-summary-table-tab4\">\n" +
          "             <div class=\"block\">\n" +
          "              This safelist allows the same text tags as <a href=\"#basic()\"><code>basic()</code></a>, and also allows <code>img</code> tags, with appropriate attributes, with <code>src</code> pointing to <code>http</code> or <code>https</code>.\n" +
          "             </div>\n" +
          "            </div>\n" +
          "            <div class=\"col-first even-row-color method-summary-table method-summary-table-tab2 method-summary-table-tab4\">\n" +
          "             <code>protected boolean</code>\n" +
          "            </div>\n" +
          "            <div class=\"col-second even-row-color method-summary-table method-summary-table-tab2 method-summary-table-tab4\">\n" +
          "             <code><a href=\"#isSafeAttribute(java.lang.String,org.jsoup.nodes.Element,org.jsoup.nodes.Attribute)\" class=\"member-name-link\">isSafeAttribute</a><wbr>(<a href=\"https://docs.oracle.com/javase/8/docs/api/java/lang/String.html\" title=\"class or interface in java.lang\" class=\"external-link\">String</a>&nbsp;tagName, <a href=\"../nodes/Element.html\" title=\"class in org.jsoup.nodes\">Element</a>&nbsp;el, <a href=\"../nodes/Attribute.html\" title=\"class in org.jsoup.nodes\">Attribute</a>&nbsp;attr)</code>\n" +
          "            </div>\n" +
          "            <div class=\"col-last even-row-color method-summary-table method-summary-table-tab2 method-summary-table-tab4\">\n" +
          "             <div class=\"block\">\n" +
          "              Test if the supplied attribute is allowed by this safelist for this tag\n" +
          "             </div>\n" +
          "            </div>\n" +
          "            <div class=\"col-first odd-row-color method-summary-table method-summary-table-tab2 method-summary-table-tab4\">\n" +
          "             <code>protected boolean</code>\n" +
          "            </div>\n" +
          "            <div class=\"col-second odd-row-color method-summary-table method-summary-table-tab2 method-summary-table-tab4\">\n" +
          "             <code><a href=\"#isSafeTag(java.lang.String)\" class=\"member-name-link\">isSafeTag</a><wbr>(<a href=\"https://docs.oracle.com/javase/8/docs/api/java/lang/String.html\" title=\"class or interface in java.lang\" class=\"external-link\">String</a>&nbsp;tag)</code>\n" +
          "            </div>\n" +
          "            <div class=\"col-last odd-row-color method-summary-table method-summary-table-tab2 method-summary-table-tab4\">\n" +
          "             <div class=\"block\">\n" +
          "              Test if the supplied tag is allowed by this safelist\n" +
          "             </div>\n" +
          "            </div>\n" +
          "            <div class=\"col-first even-row-color method-summary-table method-summary-table-tab1 method-summary-table-tab4\">\n" +
          "             <code>static <a href=\"Safelist.html\" title=\"class in org.jsoup.safety\">Safelist</a></code>\n" +
          "            </div>\n" +
          "            <div class=\"col-second even-row-color method-summary-table method-summary-table-tab1 method-summary-table-tab4\">\n" +
          "             <code><a href=\"#none()\" class=\"member-name-link\">none</a>()</code>\n" +
          "            </div>\n" +
          "            <div class=\"col-last even-row-color method-summary-table method-summary-table-tab1 method-summary-table-tab4\">\n" +
          "             <div class=\"block\">\n" +
          "              This safelist allows only text nodes: any HTML Element or any Node other than a TextNode will be removed.\n" +
          "             </div>\n" +
          "            </div>\n" +
          "            <div class=\"col-first odd-row-color method-summary-table method-summary-table-tab2 method-summary-table-tab4\">\n" +
          "             <code><a href=\"Safelist.html\" title=\"class in org.jsoup.safety\">Safelist</a></code>\n" +
          "            </div>\n" +
          "            <div class=\"col-second odd-row-color method-summary-table method-summary-table-tab2 method-summary-table-tab4\">\n" +
          "             <code><a href=\"#preserveRelativeLinks(boolean)\" class=\"member-name-link\">preserveRelativeLinks</a><wbr>(boolean&nbsp;preserve)</code>\n" +
          "            </div>\n" +
          "            <div class=\"col-last odd-row-color method-summary-table method-summary-table-tab2 method-summary-table-tab4\">\n" +
          "             <div class=\"block\">\n" +
          "              Configure this Safelist to preserve relative links in an element's URL attribute, or convert them to absolute links.\n" +
          "             </div>\n" +
          "            </div>\n" +
          "            <div class=\"col-first even-row-color method-summary-table method-summary-table-tab1 method-summary-table-tab4\">\n" +
          "             <code>static <a href=\"Safelist.html\" title=\"class in org.jsoup.safety\">Safelist</a></code>\n" +
          "            </div>\n" +
          "            <div class=\"col-second even-row-color method-summary-table method-summary-table-tab1 method-summary-table-tab4\">\n" +
          "             <code><a href=\"#relaxed()\" class=\"member-name-link\">relaxed</a>()</code>\n" +
          "            </div>\n" +
          "            <div class=\"col-last even-row-color method-summary-table method-summary-table-tab1 method-summary-table-tab4\">\n" +
          "             <div class=\"block\">\n" +
          "              This safelist allows a full range of text and structural body HTML: <code>a, b, blockquote, br, caption, cite, code, col, colgroup, dd, div, dl, dt, em, h1, h2, h3, h4, h5, h6, i, img, li, ol, p, pre, q, small, span, strike, strong, sub, sup, table, tbody, td, tfoot, th, thead, tr, u, ul</code>\n" +
          "             </div>\n" +
          "            </div>\n" +
          "            <div class=\"col-first odd-row-color method-summary-table method-summary-table-tab2 method-summary-table-tab4\">\n" +
          "             <code><a href=\"Safelist.html\" title=\"class in org.jsoup.safety\">Safelist</a></code>\n" +
          "            </div>\n" +
          "            <div class=\"col-second odd-row-color method-summary-table method-summary-table-tab2 method-summary-table-tab4\">\n" +
          "             <code><a href=\"#removeAttributes(java.lang.String,java.lang.String...)\" class=\"member-name-link\">removeAttributes</a><wbr>(<a href=\"https://docs.oracle.com/javase/8/docs/api/java/lang/String.html\" title=\"class or interface in java.lang\" class=\"external-link\">String</a>&nbsp;tag, <a href=\"https://docs.oracle.com/javase/8/docs/api/java/lang/String.html\" title=\"class or interface in java.lang\" class=\"external-link\">String</a>...&nbsp;attributes)</code>\n" +
          "            </div>\n" +
          "            <div class=\"col-last odd-row-color method-summary-table method-summary-table-tab2 method-summary-table-tab4\">\n" +
          "             <div class=\"block\">\n" +
          "              Remove a list of allowed attributes from a tag.\n" +
          "             </div>\n" +
          "            </div>\n" +
          "            <div class=\"col-first even-row-color method-summary-table method-summary-table-tab2 method-summary-table-tab4\">\n" +
          "             <code><a href=\"Safelist.html\" title=\"class in org.jsoup.safety\">Safelist</a></code>\n" +
          "            </div>\n" +
          "            <div class=\"col-second even-row-color method-summary-table method-summary-table-tab2 method-summary-table-tab4\">\n" +
          "             <code><a href=\"#removeEnforcedAttribute(java.lang.String,java.lang.String)\" class=\"member-name-link\">removeEnforcedAttribute</a><wbr>(<a href=\"https://docs.oracle.com/javase/8/docs/api/java/lang/String.html\" title=\"class or interface in java.lang\" class=\"external-link\">String</a>&nbsp;tag, <a href=\"https://docs.oracle.com/javase/8/docs/api/java/lang/String.html\" title=\"class or interface in java.lang\" class=\"external-link\">String</a>&nbsp;attribute)</code>\n" +
          "            </div>\n" +
          "            <div class=\"col-last even-row-color method-summary-table method-summary-table-tab2 method-summary-table-tab4\">\n" +
          "             <div class=\"block\">\n" +
          "              Remove a previously configured enforced attribute from a tag.\n" +
          "             </div>\n" +
          "            </div>\n" +
          "            <div class=\"col-first odd-row-color method-summary-table method-summary-table-tab2 method-summary-table-tab4\">\n" +
          "             <code><a href=\"Safelist.html\" title=\"class in org.jsoup.safety\">Safelist</a></code>\n" +
          "            </div>\n" +
          "            <div class=\"col-second odd-row-color method-summary-table method-summary-table-tab2 method-summary-table-tab4\">\n" +
          "             <code><a href=\"#removeProtocols(java.lang.String,java.lang.String,java.lang.String...)\" class=\"member-name-link\">removeProtocols</a><wbr>(<a href=\"https://docs.oracle.com/javase/8/docs/api/java/lang/String.html\" title=\"class or interface in java.lang\" class=\"external-link\">String</a>&nbsp;tag, <a href=\"https://docs.oracle.com/javase/8/docs/api/java/lang/String.html\" title=\"class or interface in java.lang\" class=\"external-link\">String</a>&nbsp;attribute, <a href=\"https://docs.oracle.com/javase/8/docs/api/java/lang/String.html\" title=\"class or interface in java.lang\" class=\"external-link\">String</a>...&nbsp;removeProtocols)</code>\n" +
          "            </div>\n" +
          "            <div class=\"col-last odd-row-color method-summary-table method-summary-table-tab2 method-summary-table-tab4\">\n" +
          "             <div class=\"block\">\n" +
          "              Remove allowed URL protocols for an element's URL attribute.\n" +
          "             </div>\n" +
          "            </div>\n" +
          "            <div class=\"col-first even-row-color method-summary-table method-summary-table-tab2 method-summary-table-tab4\">\n" +
          "             <code><a href=\"Safelist.html\" title=\"class in org.jsoup.safety\">Safelist</a></code>\n" +
          "            </div>\n" +
          "            <div class=\"col-second even-row-color method-summary-table method-summary-table-tab2 method-summary-table-tab4\">\n" +
          "             <code><a href=\"#removeTags(java.lang.String...)\" class=\"member-name-link\">removeTags</a><wbr>(<a href=\"https://docs.oracle.com/javase/8/docs/api/java/lang/String.html\" title=\"class or interface in java.lang\" class=\"external-link\">String</a>...&nbsp;tags)</code>\n" +
          "            </div>\n" +
          "            <div class=\"col-last even-row-color method-summary-table method-summary-table-tab2 method-summary-table-tab4\">\n" +
          "             <div class=\"block\">\n" +
          "              Remove a list of allowed elements from a safelist.\n" +
          "             </div>\n" +
          "            </div>\n" +
          "            <div class=\"col-first odd-row-color method-summary-table method-summary-table-tab1 method-summary-table-tab4\">\n" +
          "             <code>static <a href=\"Safelist.html\" title=\"class in org.jsoup.safety\">Safelist</a></code>\n" +
          "            </div>\n" +
          "            <div class=\"col-second odd-row-color method-summary-table method-summary-table-tab1 method-summary-table-tab4\">\n" +
          "             <code><a href=\"#simpleText()\" class=\"member-name-link\">simpleText</a>()</code>\n" +
          "            </div>\n" +
          "            <div class=\"col-last odd-row-color method-summary-table method-summary-table-tab1 method-summary-table-tab4\">\n" +
          "             <div class=\"block\">\n" +
          "              This safelist allows only simple text formatting: <code>b, em, i, strong, u</code>.\n" +
          "             </div>\n" +
          "            </div>\n" +
          "           </div>\n" +
          "          </div>\n" +
          "         </div>\n" +
          "         <div class=\"inherited-list\">\n" +
          "          <h3 id=\"methods-inherited-from-class-java.lang.Object\">Methods inherited from class&nbsp;java.lang.<a href=\"https://docs.oracle.com/javase/8/docs/api/java/lang/Object.html\" title=\"class or interface in java.lang\" class=\"external-link\">Object</a></h3> <code><a href=\"https://docs.oracle.com/javase/8/docs/api/java/lang/Object.html#clone()\" title=\"class or interface in java.lang\" class=\"external-link\">clone</a>, <a href=\"https://docs.oracle.com/javase/8/docs/api/java/lang/Object.html#equals(java.lang.Object)\" title=\"class or interface in java.lang\" class=\"external-link\">equals</a>, <a href=\"https://docs.oracle.com/javase/8/docs/api/java/lang/Object.html#finalize()\" title=\"class or interface in java.lang\" class=\"external-link\">finalize</a>, <a href=\"https://docs.oracle.com/javase/8/docs/api/java/lang/Object.html#getClass()\" title=\"class or interface in java.lang\" class=\"external-link\">getClass</a>, <a href=\"https://docs.oracle.com/javase/8/docs/api/java/lang/Object.html#hashCode()\" title=\"class or interface in java.lang\" class=\"external-link\">hashCode</a>, <a href=\"https://docs.oracle.com/javase/8/docs/api/java/lang/Object.html#notify()\" title=\"class or interface in java.lang\" class=\"external-link\">notify</a>, <a href=\"https://docs.oracle.com/javase/8/docs/api/java/lang/Object.html#notifyAll()\" title=\"class or interface in java.lang\" class=\"external-link\">notifyAll</a>, <a href=\"https://docs.oracle.com/javase/8/docs/api/java/lang/Object.html#toString()\" title=\"class or interface in java.lang\" class=\"external-link\">toString</a>, <a href=\"https://docs.oracle.com/javase/8/docs/api/java/lang/Object.html#wait()\" title=\"class or interface in java.lang\" class=\"external-link\">wait</a>, <a href=\"https://docs.oracle.com/javase/8/docs/api/java/lang/Object.html#wait(long)\" title=\"class or interface in java.lang\" class=\"external-link\">wait</a>, <a href=\"https://docs.oracle.com/javase/8/docs/api/java/lang/Object.html#wait(long,int)\" title=\"class or interface in java.lang\" class=\"external-link\">wait</a></code>\n" +
          "         </div>\n" +
          "        </section></li>\n" +
          "      </ul>\n" +
          "     </section>\n" +
          "     <section class=\"details\">\n" +
          "      <ul class=\"details-list\"><!-- ========= CONSTRUCTOR DETAIL ======== -->\n" +
          "       <li>\n" +
          "        <section class=\"constructor-details\" id=\"constructor-detail\">\n" +
          "         <h2>Constructor Details</h2>\n" +
          "         <ul class=\"member-list\">\n" +
          "          <li>\n" +
          "           <section class=\"detail\" id=\"<init>()\">\n" +
          "            <h3>Safelist</h3>\n" +
          "            <div class=\"member-signature\">\n" +
          "             <span class=\"modifiers\">public</span>&nbsp;<span class=\"element-name\">Safelist</span>()\n" +
          "            </div>\n" +
          "            <div class=\"block\">\n" +
          "             Create a new, empty safelist. Generally it will be better to start with a default prepared safelist instead.\n" +
          "            </div>\n" +
          "            <dl class=\"notes\">\n" +
          "             <dt>\n" +
          "              See Also:\n" +
          "             </dt>\n" +
          "             <dd>\n" +
          "              <ul class=\"see-list\">\n" +
          "               <li><a href=\"#basic()\"><code>basic()</code></a></li>\n" +
          "               <li><a href=\"#basicWithImages()\"><code>basicWithImages()</code></a></li>\n" +
          "               <li><a href=\"#simpleText()\"><code>simpleText()</code></a></li>\n" +
          "               <li><a href=\"#relaxed()\"><code>relaxed()</code></a></li>\n" +
          "              </ul>\n" +
          "             </dd>\n" +
          "            </dl>\n" +
          "           </section></li>\n" +
          "          <li>\n" +
          "           <section class=\"detail\" id=\"<init>(org.jsoup.safety.Safelist)\">\n" +
          "            <h3>Safelist</h3>\n" +
          "            <div class=\"member-signature\">\n" +
          "             <span class=\"modifiers\">public</span>&nbsp;<span class=\"element-name\">Safelist</span><wbr><span class=\"parameters\">(<a href=\"Safelist.html\" title=\"class in org.jsoup.safety\">Safelist</a>&nbsp;copy)</span>\n" +
          "            </div>\n" +
          "            <div class=\"block\">\n" +
          "             Deep copy an existing Safelist to a new Safelist.\n" +
          "            </div>\n" +
          "            <dl class=\"notes\">\n" +
          "             <dt>\n" +
          "              Parameters:\n" +
          "             </dt>\n" +
          "             <dd>\n" +
          "              <code>copy</code> - the Safelist to copy\n" +
          "             </dd>\n" +
          "            </dl>\n" +
          "           </section></li>\n" +
          "         </ul>\n" +
          "        </section></li> <!-- ============ METHOD DETAIL ========== -->\n" +
          "       <li>\n" +
          "        <section class=\"method-details\" id=\"method-detail\">\n" +
          "         <h2>Method Details</h2>\n" +
          "         <ul class=\"member-list\">\n" +
          "          <li>\n" +
          "           <section class=\"detail\" id=\"none()\">\n" +
          "            <h3>none</h3>\n" +
          "            <div class=\"member-signature\">\n" +
          "             <span class=\"modifiers\">public static</span>&nbsp;<span class=\"return-type\"><a href=\"Safelist.html\" title=\"class in org.jsoup.safety\">Safelist</a></span>&nbsp;<span class=\"element-name\">none</span>()\n" +
          "            </div>\n" +
          "            <div class=\"block\">\n" +
          "             This safelist allows only text nodes: any HTML Element or any Node other than a TextNode will be removed. \n" +
          "             <p>Note that the output of <a href=\"../Jsoup.html#clean(java.lang.String,org.jsoup.safety.Safelist)\"><code>Jsoup.clean(String, Safelist)</code></a> is still <b>HTML</b> even when using this Safelist, and so any HTML entities in the output will be appropriately escaped. If you want plain text, not HTML, you should use a text method such as <a href=\"../nodes/Element.html#text()\"><code>Element.text()</code></a> instead, after cleaning the document.</p>\n" +
          "             <p>Example:</p>\n" +
          "             <pre><code>\n" +
          "     String sourceBodyHtml = \"&lt;p&gt;5 is &amp;lt; 6.&lt;/p&gt;\";\n" +
          "     String html = Jsoup.clean(sourceBodyHtml, Safelist.none());\n" +
          "\n" +
          "     Cleaner cleaner = new Cleaner(Safelist.none());\n" +
          "     String text = cleaner.clean(Jsoup.parse(sourceBodyHtml)).text();\n" +
          "\n" +
          "     // html is: 5 is &amp;lt; 6.\n" +
          "     // text is: 5 is &lt; 6.\n" +
          "     </code></pre>\n" +
          "            </div>\n" +
          "            <dl class=\"notes\">\n" +
          "             <dt>\n" +
          "              Returns:\n" +
          "             </dt>\n" +
          "             <dd>\n" +
          "              safelist\n" +
          "             </dd>\n" +
          "            </dl>\n" +
          "           </section></li>\n" +
          "          <li>\n" +
          "           <section class=\"detail\" id=\"simpleText()\">\n" +
          "            <h3>simpleText</h3>\n" +
          "            <div class=\"member-signature\">\n" +
          "             <span class=\"modifiers\">public static</span>&nbsp;<span class=\"return-type\"><a href=\"Safelist.html\" title=\"class in org.jsoup.safety\">Safelist</a></span>&nbsp;<span class=\"element-name\">simpleText</span>()\n" +
          "            </div>\n" +
          "            <div class=\"block\">\n" +
          "             This safelist allows only simple text formatting: <code>b, em, i, strong, u</code>. All other HTML (tags and attributes) will be removed.\n" +
          "            </div>\n" +
          "            <dl class=\"notes\">\n" +
          "             <dt>\n" +
          "              Returns:\n" +
          "             </dt>\n" +
          "             <dd>\n" +
          "              safelist\n" +
          "             </dd>\n" +
          "            </dl>\n" +
          "           </section></li>\n" +
          "          <li>\n" +
          "           <section class=\"detail\" id=\"basic()\">\n" +
          "            <h3>basic</h3>\n" +
          "            <div class=\"member-signature\">\n" +
          "             <span class=\"modifiers\">public static</span>&nbsp;<span class=\"return-type\"><a href=\"Safelist.html\" title=\"class in org.jsoup.safety\">Safelist</a></span>&nbsp;<span class=\"element-name\">basic</span>()\n" +
          "            </div>\n" +
          "            <div class=\"block\">\n" +
          "             <p>This safelist allows a fuller range of text nodes: <code>a, b, blockquote, br, cite, code, dd, dl, dt, em, i, li, ol, p, pre, q, small, span, strike, strong, sub, sup, u, ul</code>, and appropriate attributes.</p>\n" +
          "             <p>Links (<code>a</code> elements) can point to <code>http, https, ftp, mailto</code>, and have an enforced <code>rel=nofollow</code> attribute.</p>\n" +
          "             <p>Does not allow images.</p>\n" +
          "            </div>\n" +
          "            <dl class=\"notes\">\n" +
          "             <dt>\n" +
          "              Returns:\n" +
          "             </dt>\n" +
          "             <dd>\n" +
          "              safelist\n" +
          "             </dd>\n" +
          "            </dl>\n" +
          "           </section></li>\n" +
          "          <li>\n" +
          "           <section class=\"detail\" id=\"basicWithImages()\">\n" +
          "            <h3>basicWithImages</h3>\n" +
          "            <div class=\"member-signature\">\n" +
          "             <span class=\"modifiers\">public static</span>&nbsp;<span class=\"return-type\"><a href=\"Safelist.html\" title=\"class in org.jsoup.safety\">Safelist</a></span>&nbsp;<span class=\"element-name\">basicWithImages</span>()\n" +
          "            </div>\n" +
          "            <div class=\"block\">\n" +
          "             This safelist allows the same text tags as <a href=\"#basic()\"><code>basic()</code></a>, and also allows <code>img</code> tags, with appropriate attributes, with <code>src</code> pointing to <code>http</code> or <code>https</code>.\n" +
          "            </div>\n" +
          "            <dl class=\"notes\">\n" +
          "             <dt>\n" +
          "              Returns:\n" +
          "             </dt>\n" +
          "             <dd>\n" +
          "              safelist\n" +
          "             </dd>\n" +
          "            </dl>\n" +
          "           </section></li>\n" +
          "          <li>\n" +
          "           <section class=\"detail\" id=\"relaxed()\">\n" +
          "            <h3>relaxed</h3>\n" +
          "            <div class=\"member-signature\">\n" +
          "             <span class=\"modifiers\">public static</span>&nbsp;<span class=\"return-type\"><a href=\"Safelist.html\" title=\"class in org.jsoup.safety\">Safelist</a></span>&nbsp;<span class=\"element-name\">relaxed</span>()\n" +
          "            </div>\n" +
          "            <div class=\"block\">\n" +
          "             This safelist allows a full range of text and structural body HTML: <code>a, b, blockquote, br, caption, cite, code, col, colgroup, dd, div, dl, dt, em, h1, h2, h3, h4, h5, h6, i, img, li, ol, p, pre, q, small, span, strike, strong, sub, sup, table, tbody, td, tfoot, th, thead, tr, u, ul</code>\n" +
          "             <p>Links do not have an enforced <code>rel=nofollow</code> attribute, but you can add that if desired.</p>\n" +
          "            </div>\n" +
          "            <dl class=\"notes\">\n" +
          "             <dt>\n" +
          "              Returns:\n" +
          "             </dt>\n" +
          "             <dd>\n" +
          "              safelist\n" +
          "             </dd>\n" +
          "            </dl>\n" +
          "           </section></li>\n" +
          "          <li>\n" +
          "           <section class=\"detail\" id=\"addTags(java.lang.String...)\">\n" +
          "            <h3>addTags</h3>\n" +
          "            <div class=\"member-signature\">\n" +
          "             <span class=\"modifiers\">public</span>&nbsp;<span class=\"return-type\"><a href=\"Safelist.html\" title=\"class in org.jsoup.safety\">Safelist</a></span>&nbsp;<span class=\"element-name\">addTags</span><wbr><span class=\"parameters\">(<a href=\"https://docs.oracle.com/javase/8/docs/api/java/lang/String.html\" title=\"class or interface in java.lang\" class=\"external-link\">String</a>...&nbsp;tags)</span>\n" +
          "            </div>\n" +
          "            <div class=\"block\">\n" +
          "             Add a list of allowed elements to a safelist. (If a tag is not allowed, it will be removed from the HTML.)\n" +
          "            </div>\n" +
          "            <dl class=\"notes\">\n" +
          "             <dt>\n" +
          "              Parameters:\n" +
          "             </dt>\n" +
          "             <dd>\n" +
          "              <code>tags</code> - tag names to allow\n" +
          "             </dd>\n" +
          "             <dt>\n" +
          "              Returns:\n" +
          "             </dt>\n" +
          "             <dd>\n" +
          "              this (for chaining)\n" +
          "             </dd>\n" +
          "            </dl>\n" +
          "           </section></li>\n" +
          "          <li>\n" +
          "           <section class=\"detail\" id=\"removeTags(java.lang.String...)\">\n" +
          "            <h3>removeTags</h3>\n" +
          "            <div class=\"member-signature\">\n" +
          "             <span class=\"modifiers\">public</span>&nbsp;<span class=\"return-type\"><a href=\"Safelist.html\" title=\"class in org.jsoup.safety\">Safelist</a></span>&nbsp;<span class=\"element-name\">removeTags</span><wbr><span class=\"parameters\">(<a href=\"https://docs.oracle.com/javase/8/docs/api/java/lang/String.html\" title=\"class or interface in java.lang\" class=\"external-link\">String</a>...&nbsp;tags)</span>\n" +
          "            </div>\n" +
          "            <div class=\"block\">\n" +
          "             Remove a list of allowed elements from a safelist. (If a tag is not allowed, it will be removed from the HTML.)\n" +
          "            </div>\n" +
          "            <dl class=\"notes\">\n" +
          "             <dt>\n" +
          "              Parameters:\n" +
          "             </dt>\n" +
          "             <dd>\n" +
          "              <code>tags</code> - tag names to disallow\n" +
          "             </dd>\n" +
          "             <dt>\n" +
          "              Returns:\n" +
          "             </dt>\n" +
          "             <dd>\n" +
          "              this (for chaining)\n" +
          "             </dd>\n" +
          "            </dl>\n" +
          "           </section></li>\n" +
          "          <li>\n" +
          "           <section class=\"detail\" id=\"addAttributes(java.lang.String,java.lang.String...)\">\n" +
          "            <h3>addAttributes</h3>\n" +
          "            <div class=\"member-signature\">\n" +
          "             <span class=\"modifiers\">public</span>&nbsp;<span class=\"return-type\"><a href=\"Safelist.html\" title=\"class in org.jsoup.safety\">Safelist</a></span>&nbsp;<span class=\"element-name\">addAttributes</span><wbr><span class=\"parameters\">(<a href=\"https://docs.oracle.com/javase/8/docs/api/java/lang/String.html\" title=\"class or interface in java.lang\" class=\"external-link\">String</a>&nbsp;tag, <a href=\"https://docs.oracle.com/javase/8/docs/api/java/lang/String.html\" title=\"class or interface in java.lang\" class=\"external-link\">String</a>...&nbsp;attributes)</span>\n" +
          "            </div>\n" +
          "            <div class=\"block\">\n" +
          "             Add a list of allowed attributes to a tag. (If an attribute is not allowed on an element, it will be removed.) \n" +
          "             <p>E.g.: <code>addAttributes(\"a\", \"href\", \"class\")</code> allows <code>href</code> and <code>class</code> attributes on <code>a</code> tags.</p>\n" +
          "             <p>To make an attribute valid for <b>all tags</b>, use the pseudo tag <code>:all</code>, e.g. <code>addAttributes(\":all\", \"class\")</code>.</p>\n" +
          "            </div>\n" +
          "            <dl class=\"notes\">\n" +
          "             <dt>\n" +
          "              Parameters:\n" +
          "             </dt>\n" +
          "             <dd>\n" +
          "              <code>tag</code> - The tag the attributes are for. The tag will be added to the allowed tag list if necessary.\n" +
          "             </dd>\n" +
          "             <dd>\n" +
          "              <code>attributes</code> - List of valid attributes for the tag\n" +
          "             </dd>\n" +
          "             <dt>\n" +
          "              Returns:\n" +
          "             </dt>\n" +
          "             <dd>\n" +
          "              this (for chaining)\n" +
          "             </dd>\n" +
          "            </dl>\n" +
          "           </section></li>\n" +
          "          <li>\n" +
          "           <section class=\"detail\" id=\"removeAttributes(java.lang.String,java.lang.String...)\">\n" +
          "            <h3>removeAttributes</h3>\n" +
          "            <div class=\"member-signature\">\n" +
          "             <span class=\"modifiers\">public</span>&nbsp;<span class=\"return-type\"><a href=\"Safelist.html\" title=\"class in org.jsoup.safety\">Safelist</a></span>&nbsp;<span class=\"element-name\">removeAttributes</span><wbr><span class=\"parameters\">(<a href=\"https://docs.oracle.com/javase/8/docs/api/java/lang/String.html\" title=\"class or interface in java.lang\" class=\"external-link\">String</a>&nbsp;tag, <a href=\"https://docs.oracle.com/javase/8/docs/api/java/lang/String.html\" title=\"class or interface in java.lang\" class=\"external-link\">String</a>...&nbsp;attributes)</span>\n" +
          "            </div>\n" +
          "            <div class=\"block\">\n" +
          "             Remove a list of allowed attributes from a tag. (If an attribute is not allowed on an element, it will be removed.) \n" +
          "             <p>E.g.: <code>removeAttributes(\"a\", \"href\", \"class\")</code> disallows <code>href</code> and <code>class</code> attributes on <code>a</code> tags.</p>\n" +
          "             <p>To make an attribute invalid for <b>all tags</b>, use the pseudo tag <code>:all</code>, e.g. <code>removeAttributes(\":all\", \"class\")</code>.</p>\n" +
          "            </div>\n" +
          "            <dl class=\"notes\">\n" +
          "             <dt>\n" +
          "              Parameters:\n" +
          "             </dt>\n" +
          "             <dd>\n" +
          "              <code>tag</code> - The tag the attributes are for.\n" +
          "             </dd>\n" +
          "             <dd>\n" +
          "              <code>attributes</code> - List of invalid attributes for the tag\n" +
          "             </dd>\n" +
          "             <dt>\n" +
          "              Returns:\n" +
          "             </dt>\n" +
          "             <dd>\n" +
          "              this (for chaining)\n" +
          "             </dd>\n" +
          "            </dl>\n" +
          "           </section></li>\n" +
          "          <li>\n" +
          "           <section class=\"detail\" id=\"addEnforcedAttribute(java.lang.String,java.lang.String,java.lang.String)\">\n" +
          "            <h3>addEnforcedAttribute</h3>\n" +
          "            <div class=\"member-signature\">\n" +
          "             <span class=\"modifiers\">public</span>&nbsp;<span class=\"return-type\"><a href=\"Safelist.html\" title=\"class in org.jsoup.safety\">Safelist</a></span>&nbsp;<span class=\"element-name\">addEnforcedAttribute</span><wbr><span class=\"parameters\">(<a href=\"https://docs.oracle.com/javase/8/docs/api/java/lang/String.html\" title=\"class or interface in java.lang\" class=\"external-link\">String</a>&nbsp;tag, <a href=\"https://docs.oracle.com/javase/8/docs/api/java/lang/String.html\" title=\"class or interface in java.lang\" class=\"external-link\">String</a>&nbsp;attribute, <a href=\"https://docs.oracle.com/javase/8/docs/api/java/lang/String.html\" title=\"class or interface in java.lang\" class=\"external-link\">String</a>&nbsp;value)</span>\n" +
          "            </div>\n" +
          "            <div class=\"block\">\n" +
          "             Add an enforced attribute to a tag. An enforced attribute will always be added to the element. If the element already has the attribute set, it will be overridden with this value. \n" +
          "             <p>E.g.: <code>addEnforcedAttribute(\"a\", \"rel\", \"nofollow\")</code> will make all <code>a</code> tags output as <code>&lt;a href=\"...\" rel=\"nofollow\"&gt;</code></p>\n" +
          "            </div>\n" +
          "            <dl class=\"notes\">\n" +
          "             <dt>\n" +
          "              Parameters:\n" +
          "             </dt>\n" +
          "             <dd>\n" +
          "              <code>tag</code> - The tag the enforced attribute is for. The tag will be added to the allowed tag list if necessary.\n" +
          "             </dd>\n" +
          "             <dd>\n" +
          "              <code>attribute</code> - The attribute name\n" +
          "             </dd>\n" +
          "             <dd>\n" +
          "              <code>value</code> - The enforced attribute value\n" +
          "             </dd>\n" +
          "             <dt>\n" +
          "              Returns:\n" +
          "             </dt>\n" +
          "             <dd>\n" +
          "              this (for chaining)\n" +
          "             </dd>\n" +
          "            </dl>\n" +
          "           </section></li>\n" +
          "          <li>\n" +
          "           <section class=\"detail\" id=\"removeEnforcedAttribute(java.lang.String,java.lang.String)\">\n" +
          "            <h3>removeEnforcedAttribute</h3>\n" +
          "            <div class=\"member-signature\">\n" +
          "             <span class=\"modifiers\">public</span>&nbsp;<span class=\"return-type\"><a href=\"Safelist.html\" title=\"class in org.jsoup.safety\">Safelist</a></span>&nbsp;<span class=\"element-name\">removeEnforcedAttribute</span><wbr><span class=\"parameters\">(<a href=\"https://docs.oracle.com/javase/8/docs/api/java/lang/String.html\" title=\"class or interface in java.lang\" class=\"external-link\">String</a>&nbsp;tag, <a href=\"https://docs.oracle.com/javase/8/docs/api/java/lang/String.html\" title=\"class or interface in java.lang\" class=\"external-link\">String</a>&nbsp;attribute)</span>\n" +
          "            </div>\n" +
          "            <div class=\"block\">\n" +
          "             Remove a previously configured enforced attribute from a tag.\n" +
          "            </div>\n" +
          "            <dl class=\"notes\">\n" +
          "             <dt>\n" +
          "              Parameters:\n" +
          "             </dt>\n" +
          "             <dd>\n" +
          "              <code>tag</code> - The tag the enforced attribute is for.\n" +
          "             </dd>\n" +
          "             <dd>\n" +
          "              <code>attribute</code> - The attribute name\n" +
          "             </dd>\n" +
          "             <dt>\n" +
          "              Returns:\n" +
          "             </dt>\n" +
          "             <dd>\n" +
          "              this (for chaining)\n" +
          "             </dd>\n" +
          "            </dl>\n" +
          "           </section></li>\n" +
          "          <li>\n" +
          "           <section class=\"detail\" id=\"preserveRelativeLinks(boolean)\">\n" +
          "            <h3>preserveRelativeLinks</h3>\n" +
          "            <div class=\"member-signature\">\n" +
          "             <span class=\"modifiers\">public</span>&nbsp;<span class=\"return-type\"><a href=\"Safelist.html\" title=\"class in org.jsoup.safety\">Safelist</a></span>&nbsp;<span class=\"element-name\">preserveRelativeLinks</span><wbr><span class=\"parameters\">(boolean&nbsp;preserve)</span>\n" +
          "            </div>\n" +
          "            <div class=\"block\">\n" +
          "             Configure this Safelist to preserve relative links in an element's URL attribute, or convert them to absolute links. By default, this is <b>false</b>: URLs will be made absolute (e.g. start with an allowed protocol, like e.g. <code>http://</code>. \n" +
          "             <p>Note that when handling relative links, the input document must have an appropriate <code>base URI</code> set when parsing, so that the link's protocol can be confirmed. Regardless of the setting of the <code>preserve relative links</code> option, the link must be resolvable against the base URI to an allowed protocol; otherwise the attribute will be removed.</p>\n" +
          "            </div>\n" +
          "            <dl class=\"notes\">\n" +
          "             <dt>\n" +
          "              Parameters:\n" +
          "             </dt>\n" +
          "             <dd>\n" +
          "              <code>preserve</code> - <code>true</code> to allow relative links, <code>false</code> (default) to deny\n" +
          "             </dd>\n" +
          "             <dt>\n" +
          "              Returns:\n" +
          "             </dt>\n" +
          "             <dd>\n" +
          "              this Safelist, for chaining.\n" +
          "             </dd>\n" +
          "             <dt>\n" +
          "              See Also:\n" +
          "             </dt>\n" +
          "             <dd>\n" +
          "              <ul class=\"see-list-long\">\n" +
          "               <li><a href=\"#addProtocols(java.lang.String,java.lang.String,java.lang.String...)\"><code>addProtocols(java.lang.String, java.lang.String, java.lang.String...)</code></a></li>\n" +
          "              </ul>\n" +
          "             </dd>\n" +
          "            </dl>\n" +
          "           </section></li>\n" +
          "          <li>\n" +
          "           <section class=\"detail\" id=\"addProtocols(java.lang.String,java.lang.String,java.lang.String...)\">\n" +
          "            <h3>addProtocols</h3>\n" +
          "            <div class=\"member-signature\">\n" +
          "             <span class=\"modifiers\">public</span>&nbsp;<span class=\"return-type\"><a href=\"Safelist.html\" title=\"class in org.jsoup.safety\">Safelist</a></span>&nbsp;<span class=\"element-name\">addProtocols</span><wbr><span class=\"parameters\">(<a href=\"https://docs.oracle.com/javase/8/docs/api/java/lang/String.html\" title=\"class or interface in java.lang\" class=\"external-link\">String</a>&nbsp;tag, <a href=\"https://docs.oracle.com/javase/8/docs/api/java/lang/String.html\" title=\"class or interface in java.lang\" class=\"external-link\">String</a>&nbsp;attribute, <a href=\"https://docs.oracle.com/javase/8/docs/api/java/lang/String.html\" title=\"class or interface in java.lang\" class=\"external-link\">String</a>...&nbsp;protocols)</span>\n" +
          "            </div>\n" +
          "            <div class=\"block\">\n" +
          "             Add allowed URL protocols for an element's URL attribute. This restricts the possible values of the attribute to URLs with the defined protocol. \n" +
          "             <p>E.g.: <code>addProtocols(\"a\", \"href\", \"ftp\", \"http\", \"https\")</code></p>\n" +
          "             <p>To allow a link to an in-page URL anchor (i.e. <code>&lt;a href=\"#anchor\"&gt;</code>, add a <code>#</code>:<br> E.g.: <code>addProtocols(\"a\", \"href\", \"#\")</code></p>\n" +
          "            </div>\n" +
          "            <dl class=\"notes\">\n" +
          "             <dt>\n" +
          "              Parameters:\n" +
          "             </dt>\n" +
          "             <dd>\n" +
          "              <code>tag</code> - Tag the URL protocol is for\n" +
          "             </dd>\n" +
          "             <dd>\n" +
          "              <code>attribute</code> - Attribute name\n" +
          "             </dd>\n" +
          "             <dd>\n" +
          "              <code>protocols</code> - List of valid protocols\n" +
          "             </dd>\n" +
          "             <dt>\n" +
          "              Returns:\n" +
          "             </dt>\n" +
          "             <dd>\n" +
          "              this, for chaining\n" +
          "             </dd>\n" +
          "            </dl>\n" +
          "           </section></li>\n" +
          "          <li>\n" +
          "           <section class=\"detail\" id=\"removeProtocols(java.lang.String,java.lang.String,java.lang.String...)\">\n" +
          "            <h3>removeProtocols</h3>\n" +
          "            <div class=\"member-signature\">\n" +
          "             <span class=\"modifiers\">public</span>&nbsp;<span class=\"return-type\"><a href=\"Safelist.html\" title=\"class in org.jsoup.safety\">Safelist</a></span>&nbsp;<span class=\"element-name\">removeProtocols</span><wbr><span class=\"parameters\">(<a href=\"https://docs.oracle.com/javase/8/docs/api/java/lang/String.html\" title=\"class or interface in java.lang\" class=\"external-link\">String</a>&nbsp;tag, <a href=\"https://docs.oracle.com/javase/8/docs/api/java/lang/String.html\" title=\"class or interface in java.lang\" class=\"external-link\">String</a>&nbsp;attribute, <a href=\"https://docs.oracle.com/javase/8/docs/api/java/lang/String.html\" title=\"class or interface in java.lang\" class=\"external-link\">String</a>...&nbsp;removeProtocols)</span>\n" +
          "            </div>\n" +
          "            <div class=\"block\">\n" +
          "             Remove allowed URL protocols for an element's URL attribute. If you remove all protocols for an attribute, that attribute will allow any protocol. \n" +
          "             <p>E.g.: <code>removeProtocols(\"a\", \"href\", \"ftp\")</code></p>\n" +
          "            </div>\n" +
          "            <dl class=\"notes\">\n" +
          "             <dt>\n" +
          "              Parameters:\n" +
          "             </dt>\n" +
          "             <dd>\n" +
          "              <code>tag</code> - Tag the URL protocol is for\n" +
          "             </dd>\n" +
          "             <dd>\n" +
          "              <code>attribute</code> - Attribute name\n" +
          "             </dd>\n" +
          "             <dd>\n" +
          "              <code>removeProtocols</code> - List of invalid protocols\n" +
          "             </dd>\n" +
          "             <dt>\n" +
          "              Returns:\n" +
          "             </dt>\n" +
          "             <dd>\n" +
          "              this, for chaining\n" +
          "             </dd>\n" +
          "            </dl>\n" +
          "           </section></li>\n" +
          "          <li>\n" +
          "           <section class=\"detail\" id=\"isSafeTag(java.lang.String)\">\n" +
          "            <h3>isSafeTag</h3>\n" +
          "            <div class=\"member-signature\">\n" +
          "             <span class=\"modifiers\">protected</span>&nbsp;<span class=\"return-type\">boolean</span>&nbsp;<span class=\"element-name\">isSafeTag</span><wbr><span class=\"parameters\">(<a href=\"https://docs.oracle.com/javase/8/docs/api/java/lang/String.html\" title=\"class or interface in java.lang\" class=\"external-link\">String</a>&nbsp;tag)</span>\n" +
          "            </div>\n" +
          "            <div class=\"block\">\n" +
          "             Test if the supplied tag is allowed by this safelist\n" +
          "            </div>\n" +
          "            <dl class=\"notes\">\n" +
          "             <dt>\n" +
          "              Parameters:\n" +
          "             </dt>\n" +
          "             <dd>\n" +
          "              <code>tag</code> - test tag\n" +
          "             </dd>\n" +
          "             <dt>\n" +
          "              Returns:\n" +
          "             </dt>\n" +
          "             <dd>\n" +
          "              true if allowed\n" +
          "             </dd>\n" +
          "            </dl>\n" +
          "           </section></li>\n" +
          "          <li>\n" +
          "           <section class=\"detail\" id=\"isSafeAttribute(java.lang.String,org.jsoup.nodes.Element,org.jsoup.nodes.Attribute)\">\n" +
          "            <h3>isSafeAttribute</h3>\n" +
          "            <div class=\"member-signature\">\n" +
          "             <span class=\"modifiers\">protected</span>&nbsp;<span class=\"return-type\">boolean</span>&nbsp;<span class=\"element-name\">isSafeAttribute</span><wbr><span class=\"parameters\">(<a href=\"https://docs.oracle.com/javase/8/docs/api/java/lang/String.html\" title=\"class or interface in java.lang\" class=\"external-link\">String</a>&nbsp;tagName, <a href=\"../nodes/Element.html\" title=\"class in org.jsoup.nodes\">Element</a>&nbsp;el, <a href=\"../nodes/Attribute.html\" title=\"class in org.jsoup.nodes\">Attribute</a>&nbsp;attr)</span>\n" +
          "            </div>\n" +
          "            <div class=\"block\">\n" +
          "             Test if the supplied attribute is allowed by this safelist for this tag\n" +
          "            </div>\n" +
          "            <dl class=\"notes\">\n" +
          "             <dt>\n" +
          "              Parameters:\n" +
          "             </dt>\n" +
          "             <dd>\n" +
          "              <code>tagName</code> - tag to consider allowing the attribute in\n" +
          "             </dd>\n" +
          "             <dd>\n" +
          "              <code>el</code> - element under test, to confirm protocol\n" +
          "             </dd>\n" +
          "             <dd>\n" +
          "              <code>attr</code> - attribute under test\n" +
          "             </dd>\n" +
          "             <dt>\n" +
          "              Returns:\n" +
          "             </dt>\n" +
          "             <dd>\n" +
          "              true if allowed\n" +
          "             </dd>\n" +
          "            </dl>\n" +
          "           </section></li>\n" +
          "         </ul>\n" +
          "        </section></li>\n" +
          "      </ul>\n" +
          "     </section> <!-- ========= END OF CLASS DATA ========= -->\n" +
          "    </main>\n" +
          "    <footer role=\"contentinfo\">\n" +
          "     <hr>\n" +
          "     <p class=\"legal-copy\"><small>Copyright © 2009–2023 <a href=\"https://jhy.io/\">Jonathan Hedley</a>. All rights reserved.</small></p>\n" +
          "    </footer>\n" +
          "   </div>\n" +
          "  </div>\n" +
          "  <script src=\"https://www.googletagmanager.com/gtag/js?id=G-V06EE5GKBM\" async></script>\n" +
          "  <script>window.dataLayer = window.dataLayer || []; function gtag(){dataLayer.push(arguments);} gtag('js', new Date()); gtag('config', 'G-V06EE5GKBM');</script>\n" +
          " </body>\n" +
          "</html>";
        Safelist safelist = new Safelist();
        safelist.removeAttributes("body", "html");
        String cleanText = Jsoup.clean(html, safelist);
        System.out.println(cleanText);

    }
}
