# Purpose

-  In this folder i will be learning about Dart from the book in the book folder.

-  I will follw the book step by step. this Readme file will also be updated daily with my classes topics

## Setting up dart in kali linux

Setting up Dart on Kali Linux involves a manual installation process, as Kali Linux does not typically include Dart in its package repositories. Here's how you can install Dart on Kali Linux:

### Manual Installation on Kali Linux:

1. **Download Dart SDK:**
   - Visit the [Dart SDK download page](https://dart.dev/get-dart) on your web browser.
   - Download the Dart SDK for Linux.

2. **Extract the Dart SDK:**
   - Open Terminal on Kali Linux.
   - Navigate to the directory where you downloaded the Dart SDK (usually the Downloads directory):
     ```bash
     cd ~/Downloads
     ```
   - Extract the downloaded ZIP file:
     ```bash
     tar -xvf dart-sdk-linux-x64-release.zip
     ```
   - Replace `dart-sdk-linux-x64-release.zip` with the actual name of the Dart SDK ZIP file you downloaded.

3. **Move Dart SDK to a Suitable Location:**
   - Choose a directory where you want to install Dart. For example, you can move it to `/opt` for system-wide access:
     ```bash
     sudo mv dart-sdk /opt
     ```

4. **Set Up Environment Variables:**
   - Edit your shell configuration file (`.bashrc` for Bash):
     ```bash
     nano ~/.bashrc
     ```
   - Add the following lines at the end of the file:
     ```bash
     export DART_HOME=/opt/dart-sdk
     export PATH=$PATH:$DART_HOME/bin
     ```
   - Save the file (`Ctrl+O`, `Enter`, `Ctrl+X` in Nano).

5. **Apply Changes:**
   - Reload the `.bashrc` file to apply the changes:
     ```bash
     source ~/.bashrc
     ```

6. **Verify Dart Installation:**
   - Open a new Terminal window.
   - Check Dart version to ensure it's installed correctly:
     ```bash
     dart --version
     ```

### Editor Integration:

For editing Dart code on Kali Linux, you can use editors like Visual Studio Code, IntelliJ IDEA, or any other editor that supports Dart development. Install the Dart plugin or extension for your editor to enable Dart syntax highlighting, code completion, and debugging support.

By following these steps, you should have Dart installed and ready to use on your Kali Linux system.

Chapters 1 and 2 provide a high-level introduction to the Dart language
to help you work with the basics such as variables and control flow
