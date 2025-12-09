# BMI Calculator

**Автор:** Ивайло Радев
**Факултетен номер:** 2301321046

## Описание на идеята

BMI Calculator е Android приложение, което позволява на потребителя бързо и лесно да изчислява своя Body Mass Index (BMI) на база тегло, ръст и пол. Приложението съхранява всички резултати в локална база данни, позволява преглед на историята и поддържа редактиране и изтриване на вече въведени записи. 

## Как работи

Приложението използва MVVM архитектура, Room база данни и Jetpack Compose за UI.

### Основни функционалности:

Изчисляване на BMI според тегло, ръст и пол

Автоматично определяне на категория (нормално, наднормено тегло и др.)

CRUD операции върху BMI записи: добавяне, преглед, редактиране, изтриване 

История на резултатите, подредена по дата

Persistency: всички данни се запазват локално чрез Room

UI с Material Design 3 и поддръжка на light/dark тема

## Архитектура
```
├── data/
│ ├── BmiRecord.kt
│ ├── BMIDao.kt
│ ├── BMIRepository.kt
│ └── Database.kt
├── Calculator/
│ ├── MainActivity.kt
│ ├── CalculatorScreen.kt
│ ├── CalculatorViewModel.kt
│ └── CalculatorViewModelFactory.kt
├── History/
│ ├── HistoryActivity.kt
│ ├── HistoryScreen.kt
│ ├── HistoryViewModel.kt
│ └── HistoryViewModelFactory.kt
└── ui/
└── theme/        
```
### Използвани технологииЛ 
**Kotlin**
**Jetpack Compose**
**Room Database**
**ViewModel и StateFlow**
**Coroutines**
**Material Design 3**
**Espresso и JUnit тестове**

## Потребителски поток

1. При стартиране приложението отваря Calculator Screen

2. Потребителят въвежда:

- тегло

- височина

- пол

3. Натиска „Изчисли BMI“

4. Показва се резултат със съответната категория

5. Пресметнатият BMI се записва в Room база данни

6. Потребителят може да отвори History:

- преглед на всички записи

- редакция

- изтриване

## Инсталация и стартиране

- Android Studio Hedgehog / Iguana / Jellyfish

- minSdk = 24

- targetSdk = 34–36

### Стъпки

1. Клониране на проекта:
```bash
   git clone https://github.com/IvayloRadev46/MobileApps2025.git
```
2. Отворете кроекта в Android Studio
3. Sync Gradle Files
4. Стартира се реално устройство или емулатор

## Тестови данни

| Тегло | Височина | Пол  |
| ----- | -------- | ---- |
| 70 kg | 175 cm   | Мъж  |
| 60 kg | 165 cm   | Жена |
| 90 kg | 180 cm   | Мъж  |
| 50 kg | 160 cm   | Жена |

## APK
 Release APK файлът е в `/apk/app-release.apk`

## Тестване

**Unit Test-ове** BMI логика, Repository поведением, Update логика
**UI Тест** Основен сценарии