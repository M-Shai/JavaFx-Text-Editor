import java.io.*;
import java.util.Scanner;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.CheckMenuItem;

/**
*TextEditor
*A simple text editor application
*Text area to type, load text
*Open and saves file using filechooser
*Sets text font family, style, size, weight, and color
*/
public class TextEditor extends Application
{
	private String filePath;
	private StringBuilder textFile;
	private TextArea textArea;
	private File selectedFile;
	private File saveFile;

	//writing files
	PrintWriter outputFile;
	//openfiles
	Scanner inputFile;

	private String fontSize = "18 points";
	private String color = "-fx-text-fill: black";
	private String fontFamily = "-fx-font-family: monospaced";
	private String fontStyle = "-fx-font-style: normal";
	private String fontWeight = "-fx-font-weight: normal";

	public static void main(String[] args)
	{
		// Launch the application.
		launch(args);
	}

	@Override
	public void start(Stage primaryStage)
	{
		// Constants
		final int ROW = 20;
		final int COLUMN = 40;

		//file chooser
		FileChooser fileChooser = new FileChooser();

		//Create text area
		textArea = new TextArea("Hello World");
		textArea.setPrefColumnCount(COLUMN);
		textArea.setPrefRowCount(ROW);
		textArea.setWrapText(true);
		//textArea.getText();

		// Create the menu bar.
		MenuBar menuBar = new MenuBar();

		// Create the File menu.
		Menu fileMenu = new Menu("File");
		MenuItem newItem = new MenuItem("New");
		MenuItem openItem = new MenuItem("Open");
		MenuItem saveItem = new MenuItem("Save");
		MenuItem saveAsItem = new MenuItem("Save As");
		MenuItem exitItem = new MenuItem("Exit");
		fileMenu.getItems().addAll(newItem, openItem, saveItem, saveAsItem,
			exitItem);

		//Create Font Style menu
		Menu fontStyleMenu = new Menu("Font Style");
		//Create Font Style Items
		RadioMenuItem normalStyleMI = new RadioMenuItem("Normal Style");
		RadioMenuItem monoSpaceMI = new RadioMenuItem("Monospaced");
		RadioMenuItem serifMI = new RadioMenuItem("Serif");
		RadioMenuItem sansSerifMI = new RadioMenuItem("SansSerif");
		RadioMenuItem italicMI = new RadioMenuItem("Italic");
		//selects monoSpacedMI
		normalStyleMI.setSelected(true);
		//Create Toggle Group
		ToggleGroup fontStyleTG = new ToggleGroup();
		//Add itemss to toggle group
		normalStyleMI.setToggleGroup(fontStyleTG);
		monoSpaceMI.setToggleGroup(fontStyleTG);
		serifMI.setToggleGroup(fontStyleTG);
		sansSerifMI.setToggleGroup(fontStyleTG);
		italicMI.setToggleGroup(fontStyleTG);
		//Create Weight CheckMenuItems
		CheckMenuItem normalWeightCMI = new CheckMenuItem("Normal Weight");
		CheckMenuItem boldWeightCMI = new CheckMenuItem("Bold");

		//Create Style CheckMenuItems
		CheckMenuItem normalTextCMI = new CheckMenuItem("Normal Text");
		CheckMenuItem italicTextCMI = new CheckMenuItem("Italic Text");

		//Add all items to font stryle menu
		fontStyleMenu.getItems().addAll(monoSpaceMI,serifMI,
			sansSerifMI,italicTextCMI, boldWeightCMI);

		//Create Font Size menu
		Menu fontSizeMenu = new Menu("Font Size");
		//Create font size items
		RadioMenuItem sixPtsMI = new RadioMenuItem("6 points");
		RadioMenuItem ninePtsMI = new RadioMenuItem("9 points");
		RadioMenuItem twelvePtsMI = new RadioMenuItem("12 pionts");
		RadioMenuItem eighteenPtsMI = new RadioMenuItem("18 points");
		RadioMenuItem twentyfourPtsMI = new RadioMenuItem("24 points");
		//selects 18pt
		eighteenPtsMI.setSelected(true);
		//Add items to toggle group
		ToggleGroup fontSizeTG = new ToggleGroup();
		sixPtsMI.setToggleGroup(fontSizeTG);
		ninePtsMI.setToggleGroup(fontSizeTG);
		twelvePtsMI.setToggleGroup(fontSizeTG);
		eighteenPtsMI.setToggleGroup(fontSizeTG);
		twentyfourPtsMI.setToggleGroup(fontSizeTG);
		//Add to font size menu
		fontSizeMenu.getItems().addAll(sixPtsMI, ninePtsMI, twelvePtsMI,
			eighteenPtsMI, twentyfourPtsMI);

		//Create Font Color menu
		Menu colorMenu = new Menu("Font Color");
		//Create color items
		MenuItem blkMI = new MenuItem("Black");
		blkMI.setStyle("-fx-text-fill: black");
		MenuItem redMI = new MenuItem("Red");
		redMI.setStyle("-fx-text-fill: red");
		MenuItem greenMI = new MenuItem("Green");
		greenMI.setStyle("-fx-text-fill: green");
		MenuItem blueMI = new MenuItem("Blue");
		blueMI.setStyle("-fx-text-fill: blue");
		MenuItem orangeMI = new MenuItem("Orange");
		orangeMI.setStyle("-fx-text-fill: orange");
		MenuItem yellowMI = new MenuItem("Yellow");
		yellowMI.setStyle("-fx-text-fill: yellow");
		MenuItem goldMI = new MenuItem("Gold");
		goldMI.setStyle("-fx-text-fill: gold");
		MenuItem purpleMI = new MenuItem("Purple");
		purpleMI.setStyle("-fx-text-fill: purple");

		//Add items to menu
				colorMenu.getItems().addAll(blkMI, redMI, greenMI, blueMI,
			orangeMI, yellowMI, goldMI, purpleMI);


		// Register event handler for the file menu items

		//clears text fields and file path
		//sets all font style, size, weight, color to defaults
		newItem.setOnAction(event ->
		{
			selectedFile = null;
			filePath = null;
			textArea.setText("");

			//defualts all text font
			//selects 18pt
			eighteenPtsMI.setSelected(true);

			color = "-fx-text-fill: black";
			fontFamily = "-fx-font-family: monospaced";
			fontStyle = "-fx-font-style: normal";
			fontWeight = "-fx-font-weight: normal";
			setFont(fontSize, color, fontFamily, fontStyle, fontWeight);
		});

		//Opens file chooser dialog box
		//User can choose a text file
		// Dislays text to text area
		openItem.setOnAction(event ->
		{
			//opens fileChosser in primary stage
			selectedFile = fileChooser.showOpenDialog(primaryStage);
			System.out.println("Line 119: file " + selectedFile);
			if(selectedFile != null)
			{
				filePath = selectedFile.getPath();

				System.out.println("Line 123: file path " + filePath);
				try
				{
					//scanner object to read file
					inputFile = new Scanner(selectedFile);

					//int i = 0;

					textFile = new StringBuilder();

					while(inputFile.hasNext())
					{
						//i ++;
						//System.out.println("i = " + i);
						textFile.append(inputFile.nextLine() + "\n");
						//System.out.println("textFile = " + textFile);
					}
					//System.out.println(textFile.toString());
					setFont(fontSize, color, fontFamily, fontStyle,
						fontWeight);
					textArea.setText(textFile.toString());
				}
				//catches exception
				catch(FileNotFoundException e)
				{
					//error message
					System.out.println(e);
				}


			}

		});

		//Save text file
		//Saves to current file path
		//Or save as new file
		saveItem.setOnAction(event ->
		{
			try
			{
				//If new file, file and path not known
				//opens saveDialog to choose and save file
				if(selectedFile == null)
				{
					//opens the save dialog in primary stage
					selectedFile = fileChooser.showSaveDialog(primaryStage);
					//opens file and writes to it
					outputFile = new PrintWriter(selectedFile + ".txt");
					outputFile.print(textArea.getText());
					outputFile.close();

				}
				//If file and file path is already known
				//Writes to known test file w/o saveDialog
				else
				{
					//opens file and writes to it
					outputFile = new PrintWriter(selectedFile);
					outputFile.print(textArea.getText());
					outputFile.close();
				}
			}
			catch(FileNotFoundException e)
			{
				//error message
				System.out.println(e);
			}
		});

		//File as new file
		//Sets file path to new file
		saveAsItem.setOnAction(event ->
		{
			//opens the save dialog in primary stage
			saveFile = fileChooser.showSaveDialog(primaryStage);
			//opens file and writes to it
			try
			{
				outputFile = new PrintWriter(saveFile + ".txt");
				outputFile.print(textArea.getText());
				outputFile.close();
			}
			catch(FileNotFoundException e)
			{
				//error message
				System.out.println(e);
			}
		});

		exitItem.setOnAction(event ->
		{
			primaryStage.close();
		});

		//Register my events

		//Font size actions
		sixPtsMI.setOnAction(event ->
		{
			fontSize = "-fx-font-size: 6pt";
			setFont(fontSize, color, fontFamily, fontStyle, fontWeight);
		});


		ninePtsMI.setOnAction(event ->
		{
			fontSize = "-fx-font-size: 9pt";
			setFont(fontSize, color, fontFamily, fontStyle, fontWeight);
		});

		twelvePtsMI.setOnAction(event ->
		{
			fontSize = "-fx-font-size: 12pt";
			setFont(fontSize, color, fontFamily, fontStyle, fontWeight);
		});

		eighteenPtsMI.setOnAction(event ->
		{
			fontSize = "-fx-font-size: 18pt";
			setFont(fontSize, color, fontFamily, fontStyle, fontWeight);
		});

		twentyfourPtsMI.setOnAction(event ->
		{
			fontSize = "-fx-font-size: 24pt";
			setFont(fontSize, color, fontFamily, fontStyle, fontWeight);
		});


		//Color actions
		redMI.setOnAction(event ->
		{
			color = "-fx-text-fill: red";
			setFont(fontSize, color, fontFamily, fontStyle, fontWeight);
		});

		greenMI.setOnAction(event ->
		{
			color = "-fx-text-fill: green";
			setFont(fontSize, color, fontFamily, fontStyle, fontWeight);
		});

		blueMI.setOnAction(event ->
		{
			color = "-fx-text-fill: blue";
			setFont(fontSize, color, fontFamily, fontStyle, fontWeight);
		});

		orangeMI.setOnAction(event ->
		{
			color = "-fx-text-fill: orange";
			setFont(fontSize, color, fontFamily, fontStyle, fontWeight);
		});

		yellowMI.setOnAction(event ->
		{
			color = "-fx-text-fill: yellow";
			setFont(fontSize, color, fontFamily, fontStyle, fontWeight);
		});

		goldMI.setOnAction(event ->
		{
			color = "-fx-text-fill: gold";
			setFont(fontSize, color, fontFamily, fontStyle, fontWeight);
		});

		purpleMI.setOnAction(event ->
		{
			color = "-fx-text-fill: purple";
			setFont(fontSize, color, fontFamily, fontStyle, fontWeight);
		});


		//Font family actions
		monoSpaceMI.setOnAction(event ->
		{
			fontFamily = "-fx-font-family: monospace";
			setFont(fontSize, color, fontFamily, fontStyle, fontWeight);
		});

		serifMI.setOnAction(event ->
		{
			fontFamily = "-fx-font-family: serif";
			setFont(fontSize, color, fontFamily, fontStyle, fontWeight);
		});

		sansSerifMI.setOnAction(event ->
		{
			fontFamily = "-fx-font-family: sans-serif";
			setFont(fontSize, color, fontFamily, fontStyle, fontWeight);
		});


		//Font weight action
		boldWeightCMI.setOnAction(event ->
		{
			if(boldWeightCMI.isSelected())
			{
				fontWeight= "-fx-font-weight: bold";
				setFont(fontSize, color, fontFamily, fontStyle, fontWeight);
			}
			else
			{
				fontWeight= "-fx-font-weight: normal";
				setFont(fontSize, color, fontFamily, fontStyle, fontWeight);
			}
		});


		//Font style action
		italicTextCMI.setOnAction(event ->
		{
			if(italicTextCMI.isSelected())
			{
				fontStyle = "-fx-font-style: italic";
				setFont(fontSize, color, fontFamily, fontStyle, fontWeight);
			}
			else
			{
				fontStyle = "-fx-font-style: normal";
				setFont(fontSize, color, fontFamily, fontStyle, fontWeight);
			}
		});

		// Add the menus to the menu bar
		menuBar.getMenus().addAll(fileMenu, fontStyleMenu,
			fontSizeMenu, colorMenu);

		// Create a BorderPane with text area.
		BorderPane borderPane = new BorderPane(textArea);
		// Add the menu bar to the top region.
		borderPane.setTop(menuBar);

		// Create a Scene and display it.
		Scene scene = new Scene(borderPane);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Text Editor");
		primaryStage.show();
	}

	/**
	setFont method
	sets font family, style, weight, color, and size
	@param String size, text size
	@param String color, text color
	@parma String family, font family
	@param String style, font style
	@param String wght, text weight
	*/
	private void setFont(String size, String col, String family,
		String style, String wght)
	{
		//sets fields
		fontSize = size;
		color = col;
		fontFamily = family;
		fontStyle = style;
		fontWeight = wght;
		//set text area style
		textArea.setStyle(fontSize + ";" + color + ";" + fontFamily + ";"
						+ fontStyle + ";" + fontWeight);
 	}



}
