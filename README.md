# FedulovSplatTest
Описание работы программы.

Программа написана с применением JavaFX8(графическая часть), java.io,
java.nio, java.util и filebrowsertools.
Главный управляющий класс - Controller.
Главный класс отвечающий за модель поведения приложения - Main.
Главная графическая родительская платформа - sample.fxml.

Первым запускается главное окно программы со следующими компонентами:

-) TreeView отвечает за визуализацию иерархической структуры каталогов. 
-- Реализована загрузка содержимого каталога при выделении его мышкой таким
образом,что обход выделенной директории, заполнение элементов файловой
системы соответствующими иконками и работа визуализации индикатора загрузки
в течение 2-х секунд совершается 
в отдельных потоках таким образом поток 
выполнения основного приложения 
не замораживается !
Также реализована ленивая(не рекурсивная) загрузка элементов выделяемой 
папки.
Открываемые папки меняют своё отображение на соответствующее открытой
папке);

-) TreeTable отвечает за отображение содержимого выделенного в левой 
панели элемента дерева каталогов, а также отображает имя, размер файлов 
и дату последней модификации элемента.
Панель TreeTable позволяет запускать по щелчку файлы в выделенной в 
TreeView папке.
Запуск файлов осуществляется программами указанными в настройках операционной
системы по умолчанию(за это отвечает компонент - Desktop). 

В меню главного окна реализовано добавление, удаление, открытие файлов 
(программами по умолчанию определёнными в операционной системе пользователем). 

Указанная функциональность реализована с вызовом отдельных модальных 
диалоговых окон.

В приложении используются следующие паттерны программирования:
-) основной паттерн исходя из выбранной мной библиотеки javafx - паттерн - 
MVC(Model(Main.java) View(sample.fxml) Controller(Controller.java));
-) при обходе структуры папок и заполнении выделенного элемента файловой
системы применён паттерн - фабрика;
-) для обработки событий с элементами используется паттерн - Listener 
и Наблюдатель - Observer ;
-) для выполнения действий обхода дерева и заполнения иконками элеменов 
используются слабые связи за счёт применения анонимных классов и внедрения
зависимости через конструкторы.

В папке с программой содержится документация JavaDoc по используемым классам и 
их взаимосвязям.