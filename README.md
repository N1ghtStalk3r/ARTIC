# ARTIC
Мобильное приложения для знакомства с экспонатами Чикагского института исскуств (The Art Institute of Chicago, USA) 

## Назначение

Приложения обеспечивает
 - иллюстрированный реестр экспонатов и выставок 
 - удобный поиск по названиям, и текстам каталогов музея, 
 - проигрывание звуковых сообщений для отдельных экспонатов
 - покупку и хранение билетов для посещения многочисленных выставок
 - помощь в регистрации в Google календаре  напоминания о времени работы выставки. 


## Методы и технологии

Информационная составляющая приложения всецело базируется на обширном информационном ресурсе музея  [Artic API](https://api.artic.edu/api/v1/). 
 Архитектура приложения соответствует принципам **MVVM**.
 Само приложение написано на **Kotlin** с использованием следующих библиотек и технологий
  - Retrofit
  - Ktor
  - LiveData
  - Room 
  - SQLite
  - Koin
  - Fragment navigation
  - NavComponent
  - Zxing (qr-code)
 

## Установка
Скопируйте это репозиторий и загрузите в **Android Studio**
```bash
git@github.com:N1ghtStalk3r/ARTIC.git
```

## Настройка
Приложение готово к работе "из коробки".

## Поддержка
Этот проект сопровождается:
* [N1ghtStalk3r](http://github.com/N1ghtStalk3r)
* [Zimaxim](http://github.com/Zimaxim)


## Распространение

Just fork it

## Скришоты 
<div style="width:200px">![Welcome-screen](https://images2.imgbox.com/69/0c/Kq0OvbBt_o.png)</div>
![Fragment-List](https://images2.imgbox.com/7b/4e/RRRuBnId_o.png|=x200)
![Fragment-Details](https://images2.imgbox.com/d0/c8/xhZey57o_o.png=200x360)
![Bye-ticket](https://images2.imgbox.com/fd/39/nR0Nny6E_o.gif)
![Add-Event](https://images2.imgbox.com/23/28/YLsURRLz_o.gif)

