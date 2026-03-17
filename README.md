# 📦 Packer-Unpacker Utility (Java)

A command-line based file packing and unpacking utility built in Java.  
This tool combines multiple files into a single packed file and restores them back.

---
## 🎥 Demo Video
Watch here: https://www.youtube.com/watch?v=Zaf4aL7rLWA

---
## 🚀 Features

- Pack multiple files into a single archive
- Unpack files from archive
- Custom header-based file format
- Chunk-based file processing (efficient for large files)
- Supports all file types

---

## 🛠️ Technologies Used

- Java
- File Handling (FileInputStream, FileOutputStream)
- CLI (Command Line Interface)

---

## 📂 How It Works

### Packing
- Reads all files from a directory
- Stores metadata (filename + size) in header
- Appends file data into packed file

### Unpacking
- Reads header information
- Extracts filename and size
- Recreates original files

---

## ▶️ How to Run

```bash
javac PackerUnpacker.java
java PackerUnpacker

