# Android Projects — Workspace Guide

A collection of Android example projects covering common UI components, intents, fragments, storage, media, and more.

---

## Projects in This Workspace

| Project | Description |
|---|---|
| `AutoCompleteTextViewACTV` | AutoCompleteTextView demo |
| `DifferentIntentOperations` | Various Intent operations |
| `DropdownListSpinner` | Spinner / dropdown list demo |
| `FragmentExampleApplication` | Fragment usage example |
| `IntentDemo` | Basic Intent demo |
| `MyAndroidRecorder` | Audio recorder app |
| `MyApplication` | General sandbox app |
| `MyApplication2` | General sandbox app 2 |
| `MyApplication5` | General sandbox app 5 |
| `MyApplicationactv` | AutoCompleteTextView variant |
| `MyApplicationSp` | SharedPreferences variant |
| `MyFirstButtonClickViewMessageApplication` | Button click & message display |
| `MyMediaPlayer` | Media player demo |
| `MyRecordVideoApplication` | Video recording app |
| `MySqlLiteDatabaseDemo` | SQLite database demo |
| `SharedPreferenceApplication` | SharedPreferences demo |
| `StoreReadFileDataInternalStorageList` | Internal storage read/write |
| `WebVieComponentTest` | WebView component test |
| `webviewtest` | WebView test variant |
| `WelcomeApp` | Hello World / Welcome screen |
| `WelcomeApp2` | Welcome screen variant |
| `WelcomeButton` | Button interaction demo |

---

## How to Open a Project in Android Studio

1. Open **Android Studio**
2. Click **File → Open**
3. Navigate to the workspace folder and select the **project folder** (e.g., `WelcomeApp`)
4. Click **OK** — Android Studio will sync Gradle automatically

> Each folder is a **standalone Android project**. Open them one at a time, not the root workspace folder.

---

## How to Open a Layout XML File

Layout XML files define the UI of each screen. They live here inside every project:

```
<ProjectName>/
└── app/
    └── src/
        └── main/
            └── res/
                └── layout/
                    └── activity_main.xml   ← layout file
```

### Steps to open in Android Studio

1. In the **Project** panel (left sidebar), make sure the view is set to **Android** (dropdown at the top of the panel)
2. Expand: `app → res → layout`
3. Double-click `activity_main.xml` (or any layout file)
4. The layout opens in the **Layout Editor**
   - Click **Design** tab (top-right of editor) → visual drag-and-drop view
   - Click **Code** tab → raw XML view
   - Click **Split** tab → both side by side

### Direct file path (for reference)

```
app/src/main/res/layout/activity_main.xml
```

---

## How to Open a Java / MainActivity File

Java source files (Activities, etc.) live here:

```
<ProjectName>/
└── app/
    └── src/
        └── main/
            └── java/
                └── com/
                    └── example/
                        └── <appname>/
                            └── MainActivity.java   ← activity file
```

### Steps to open in Android Studio

1. In the **Project** panel, expand: `app → java → com.example.<appname>`
2. Double-click `MainActivity.java` to open it in the editor

### Example — WelcomeApp

```
WelcomeApp/app/src/main/java/com/example/welcomeapp/MainActivity.java
```

### Example — AutoCompleteTextViewACTV

```
AutoCompleteTextViewACTV/app/src/main/java/com/example/autocompletetextviewactv/MainActivity.java
```

---

## How a Layout XML Connects to a Java Activity

Inside `MainActivity.java`, the layout is loaded with `setContentView`:

```java
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // This line links the activity to its layout XML
        setContentView(R.layout.activity_main);
    }
}
```

- `R.layout.activity_main` refers to `res/layout/activity_main.xml`
- To use a different layout, change `activity_main` to match the XML filename (without `.xml`)

### Accessing UI elements from Java

```java
// In activity_main.xml, a Button with android:id="@+id/myButton"
Button myButton = findViewById(R.id.myButton);

myButton.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        // handle click
    }
});
```

---

## Project File Structure (Quick Reference)

```
<ProjectName>/
├── app/
│   └── src/
│       └── main/
│           ├── AndroidManifest.xml          ← app config, activities declared here
│           ├── java/com/example/<app>/
│           │   └── MainActivity.java        ← Java activity code
│           └── res/
│               ├── layout/
│               │   └── activity_main.xml    ← UI layout
│               ├── values/
│               │   ├── strings.xml          ← string resources
│               │   ├── colors.xml           ← color resources
│               │   └── themes.xml           ← app theme
│               └── drawable/                ← images and icons
├── build.gradle.kts                         ← project-level build config
└── app/build.gradle.kts                     ← app-level build config (dependencies)
```

---

## Requirements

- **Android Studio** (Hedgehog or later recommended)
- **JDK 17+**
- **Android SDK** installed via Android Studio SDK Manager
- Gradle syncs automatically on project open
