# Mini-Language-Interpreter
Evaluates the result of a 'Program' having multiple statements. 

Desktop app made with JavaFx.

<img src="https://imgur.com/1NIHwS0.png" width="450">
<img src="https://imgur.com/1qd6tCO.png" width="450">

### Java FX Setup for IntelliJ
- Download JavaFX 11 or higher from Internet.
- In the download, there is a 'lib' folder. Add it to
 Project Structure -> Libraries
- Go to: Run -> Edit Configurations...-> VM Options.
Add this instruction:
```
--module-path "\path\to\javafx-sdk-12.0.2\lib" --add-modules javafx.controls,javafx.fxml
```

Example:
`
--module-path "E:\ProgramsAndFeatures\javafx-sdk-12.0.2\lib" --add-modules javafx.controls,javafx.fxml
`








