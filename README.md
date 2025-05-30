# 🗂️ Folder Organizer

A lightweight Kotlin-based CLI tool to organize messy folders (like Downloads, project dirs, etc.) by file type.

---

## ✅ Features

- Sorts files into folders like `Documents`, `Images`, `Videos`, etc.
- Supports custom extensions mapping
- Clean CLI interface
- Built as a single runnable `.jar` — no setup needed

---

## 🚀 Usage

### 📦 1. Download

Grab the latest `.jar` from [Releases](https://github.com/abhilashini/FolderOrganizer/releases)

---

### 💻 2. Run it

```bash
java -jar FolderOrganizer-fat.jar /path/to/your/folder
```

## 🔧 Build from Source
```bash
git clone https://github.com/abhilashini/FolderOrganizer.git
cd FolderOrganizer
./gradlew fatJar
```

## 🧪 Run Tests
```bash
./gradlew test
```

## 📁 Example

#### Before
![Before organizing](assets/1-before-organizing.png)

```bash
$ java -jar app/build/libs/FolderOrganizer-fat.jar test_folder/
Moved: dummy.pdf → Documents/
Moved: moodmark-preview.png → Images/
Done organizing 'test_folder'
```

#### After
![After organizing](assets/2-after-organizing.png)