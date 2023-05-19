# SearcheEngine

## Описание

Поисковый вэб-движок выполнен на Java 17, позволяющий обходить заданные в конфигурационном файле сайты,
имеет следующий функционал:

1) Обход всех страниц относящиеся к сайту (выполнение индексации);
2) Извлечение и преобразование в исходную форму всех слов отдельно от html кода страницы;
3) Запись в базу данных MySQL и Redis;
4) Выполнение индексации отдельной страницы.
5) Выполнения поиска как по всем сайтам, так и по отдельному сайту.

## Стэк используемых технологий:

1) Spring Boot;
2) Spring Data;
3) MySql;
4) Redis;
5) JSOUP;
6) Lucene morphology;
7) Lombok.

## Установка:

Для операционной системы Linux Ubuntu необходимо выполнить следующие действия:

1. Открыть терминал Linux.

![terminal](/readmiresources/img/installJava/terminal.png)
2. Выполнить команду:
    * sudo apt update
    * sudo apt install openjdk-17-jre-headless
   
3. Дождаться установки JVM.
4. Ввести команду в командной строке
* java --version

должно появится сообщение об версии JVM.\
![response](/readmiresources/img/installJava/responseVersion.png)
