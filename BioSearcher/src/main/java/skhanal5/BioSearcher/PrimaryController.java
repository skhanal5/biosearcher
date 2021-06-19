package skhanal5.BioSearcher;

import java.awt.Desktop;
import java.io.Console;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.kordamp.ikonli.javafx.FontIcon;
import org.slf4j.Logger;

import com.github.instagram4j.instagram4j.IGClient;
import com.github.instagram4j.instagram4j.exceptions.IGLoginException;
import com.github.instagram4j.instagram4j.models.user.Profile;

import javafx.animation.KeyFrame;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.util.Duration;

public class PrimaryController implements Initializable {

	@FXML
	private AnchorPane parent;

	@FXML
	private Pane signInPane;

	@FXML
	private Pane loadingPane;

	@FXML
	private Pane mainWindow;

	@FXML
	private Pane settingsPane;

	@FXML
	private Pane settingsLoad;

	@FXML
	private Pane consolePane;

	@FXML
	private Pane homePane1;

	@FXML
	private Pane homePane2;

	@FXML
	private Pane homePane3;

	@FXML
	private TextField usernameField;

	@FXML
	private TextField plaintextField;

	@FXML
	private TextField inputField;

	@FXML
	private TextField outputField;

	@FXML
	private TextField handleField;

	@FXML
	private TextField entryField;
	
	@FXML
	private TextArea consoleLogs;

	@FXML
	private PasswordField passwordField;

	@FXML
	private Button loginButton;

	@FXML
	private Button startSearch;

	@FXML
	private Button submitButton;

	@FXML
	private Button inputBrowse;

	@FXML
	private Circle loadingCircle;

	@FXML
	private Circle settingsCircle;

	@FXML
	private Label loginInvalidLabel;

	@FXML
	private Label specialCharLabel;

	@FXML
	private Label settingsWarningLabel;

	@FXML
	private Label entrySuccess;
	
	@FXML
	private Label entryFailure;

	@FXML
	private FontIcon eyeIcon;

	@FXML
	private FontIcon userIcon;

	@FXML
	private FontIcon lockIcon;

	private ArrayList<String> keywords = new ArrayList<String>();
	private List<Profile> results;
	private double xOffset = 0;
	private double yOffset = 0;
	private int pos = 0;
	private HashSet<String> followersSet = new HashSet<String>();
	private HashMap<String, ArrayList<String>> extractedList = new HashMap<String, ArrayList<String>>();
	private IGClient client;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		Pane[] infoPane = { homePane1, homePane2, homePane3 };
		Timeline fiveSecondsWonder = new Timeline(
				new KeyFrame(Duration.seconds(7), event -> {
					if (pos == infoPane.length - 1) {
						infoPane[pos].setVisible(false);
						pos = 0;
						infoPane[pos].setVisible(true);
					} else {
						infoPane[pos].setVisible(false);
						infoPane[++pos].setVisible(true);
					}
				}));
		fiveSecondsWonder.setCycleCount(Timeline.INDEFINITE);
		fiveSecondsWonder.play();

		usernameField.textProperty()
				.addListener((observable, oldValue, newValue) -> {
					if (!usernameField.getText().matches("[a-zA-Z0-9._]*")) {
						specialCharLabel
								.setText("Special characters are not allowed.");
					} else if (usernameField.getText()
							.matches("[a-zA-Z0-9._]*")) {
						specialCharLabel.setText("");
					}
				});

		usernameField.focusedProperty()
				.addListener((observable, oldValue, newValue) -> {
					if (usernameField.getText().length() == 1) {
						specialCharLabel.setText(
								"Username must be at least 1 character long");
					} else if (usernameField.getText().length() == 15) {
						usernameField.setText(
								usernameField.getText().substring(0, 15));
						specialCharLabel.setText(
								"Username must be less than 15 characters long");
					}
				});

		usernameField.lengthProperty()
				.addListener((observable, oldValue, newValue) -> {
					if (newValue.intValue() > oldValue.intValue()) {
						if (usernameField.getText().length() >= 15) {
							specialCharLabel.setText(
									"Username must be less than 15 characters long");
							usernameField.setText(
									usernameField.getText().substring(0, 15));
						}
					}
				});

		handleField.textProperty()
				.addListener((observable, oldValue, newValue) -> {
					if (!handleField.getText().matches("[a-zA-Z0-9._]*")) {
						settingsWarningLabel
								.setText("Special characters are not allowed.");
					} else if (handleField.getText()
							.matches("[a-zA-Z0-9._]*")) {
						settingsWarningLabel.setText("");
					}
				});

		handleField.focusedProperty()
				.addListener((observable, oldValue, newValue) -> {
					if (handleField.getText().length() == 1) {
						settingsWarningLabel.setText(
								"Username must be at least 1 character long");
					} else if (handleField.getText().length() == 30) {
						handleField.setText(
								handleField.getText().substring(0, 30));
						settingsWarningLabel.setText(
								"Username must be less than 30 characters long");
					}
				});

		handleField.lengthProperty()
				.addListener((observable, oldValue, newValue) -> {
					if (newValue.intValue() > oldValue.intValue()) {
						if (handleField.getText().length() >= 30) {
							settingsWarningLabel.setText(
									"Username must be less than 30 characters long");
							handleField.setText(
									handleField.getText().substring(0, 30));
						}
					}
				});

		entryField.textProperty()
				.addListener((observable, oldValue, newValue) -> {
					if (!entryField.getText().matches(
							"[a-zA-Z0-9\\t\\n ./<>?;:\"'`!@#$%^&*()\\[\\]{}_+=|\\\\-]*")) {
						settingsWarningLabel
								.setText("Special characters are not allowed.");
					} else if (entryField.getText().matches(
							"[a-zA-Z0-9\\t\\n ./<>?;:\"'`!@#$%^&*()\\[\\]{}_+=|\\\\-]*")) {
						settingsWarningLabel.setText("");
					}
				});

		entryField.focusedProperty()
				.addListener((observable, oldValue, newValue) -> {
					if (entryField.isFocused()) {
						entrySuccess.setText("");
						entryFailure.setText("");
					}
					if (entryField.getText().length() == 15) {
						entryField
								.setText(entryField.getText().substring(0, 15));
						settingsWarningLabel.setText(
								"Keywords must be less than 15 characters long");
					}
				});

		entryField.lengthProperty()
				.addListener((observable, oldValue, newValue) -> {
					if (newValue.intValue() > oldValue.intValue()) {
						if (entryField.getText().length() >= 15) {
							settingsWarningLabel.setText(
									"Keywords must be less than 15 characters long");
							entryField.setText(
									entryField.getText().substring(0, 15));
						}
					}
				});

		passwordField.lengthProperty()
				.addListener((observable, oldValue, newValue) -> {
					if (newValue.intValue() > oldValue.intValue()) {
						if (passwordField.getText().length() >= 15) {
							passwordField.setText(
									passwordField.getText().substring(0, 15));
						}
					}
				});

		plaintextField.textProperty()
				.addListener((observable, oldValue, newValue) -> {
					passwordField.setText(plaintextField.getText());
					if (plaintextField.getText().equals("")) {
						eyeIcon.setIconLiteral("fa-eye-slash");
						plaintextField.setVisible(false);
						passwordField.setVisible(true);
					}
				});

		BooleanBinding checkEntries = 
				settingsWarningLabel.textProperty().isNotEmpty()
					.or(inputField.textProperty().isNotEmpty());

		BooleanBinding checkFields = 
				specialCharLabel.textProperty().isNotEmpty()
					.or(usernameField.textProperty().length().lessThanOrEqualTo(3))
						.or(usernameField.textProperty().length().greaterThanOrEqualTo(15))
							.or(passwordField.textProperty().isEmpty());
	
		loginButton.disableProperty().bind(checkFields);
		submitButton.disableProperty().bind(checkEntries);
		
		submitButton.disableProperty()
		.addListener((observable, oldValue, newValue) -> {
			if(submitButton.isDisabled()) {
				BooleanBinding checkSettings = 
						settingsWarningLabel.textProperty().isNotEmpty()
							.or(handleField.textProperty().isEmpty())
								.or(outputField.textProperty().isEmpty());
				startSearch.disableProperty().bind(checkSettings);
			}
		});
		
		inputBrowse.disableProperty()
		.addListener((observable, oldValue, newValue) -> {
			if(inputBrowse.isDisabled()) {
				BooleanBinding checkSettings = 
						settingsWarningLabel.textProperty().isNotEmpty()
							.or(handleField.textProperty().isEmpty())
								.or(outputField.textProperty().isEmpty())
									.or(inputBrowse.disableProperty().not());
				startSearch.disableProperty().bind(checkSettings);
			}
		});
		
		startSearch.disableProperty().bind(settingsWarningLabel.textProperty().isEmpty());
		entryField.editableProperty().bind(inputField.textProperty().isEmpty());
		eyeIcon.disableProperty().bind(passwordField.textProperty().isEmpty());
		eyeIcon.visibleProperty().bind(passwordField.textProperty().isNotEmpty());
		dragStage();
	}

	private void dragStage() {
		parent.setOnMousePressed((event) -> {
			parent.requestFocus();
		});

		parent.setOnMousePressed((event) -> {
			xOffset = event.getSceneX();
			yOffset = event.getSceneY();
		});

		parent.setOnMouseDragged((event) -> {
			App.primaryStage.setX(event.getScreenX() - xOffset);
			App.primaryStage.setY(event.getScreenY() - yOffset);
		});
	}

	@FXML
	private void redirectSignIn() throws Exception {
		Desktop.getDesktop().browse(
				new URL("https://github.com/skhanal5/bioscraper").toURI());
	}

	@FXML
	private void minimizeWindow(MouseEvent event) {
		Stage s = (Stage) ((Node) event.getSource()).getScene().getWindow();
		s.setIconified(true);
	}

	@FXML
	private void closeWindow(MouseEvent event) {
		Stage s = (Stage) ((Node) event.getSource()).getScene().getWindow();
		s.close();
	}

	@FXML
	private void redirectWindow() throws Exception {
		Desktop.getDesktop().browse(
				new URL("https://www.instagram.com/accounts/password/reset/")
						.toURI());
	}

	@FXML
	private void createAccount() throws Exception {
		Desktop.getDesktop().browse(
				new URL("https://www.instagram.com/accounts/emailsignup/")
						.toURI());
	}

	@FXML
	private void revealPassword(MouseEvent event) {
		if (eyeIcon.getIconLiteral().equals("fa-eye-slash")) {
			eyeIcon.setIconLiteral("fa-eye");
			plaintextField.setText(passwordField.getText());
			plaintextField.setVisible(true);
			passwordField.setVisible(false);
		} else {
			eyeIcon.setIconLiteral("fa-eye-slash");
			passwordField.setVisible(true);
			plaintextField.setVisible(false);
		}
	}

	private void checkLogin(String username, String password) {
		new Thread(() -> {
			try {
				client = IGClient.builder().username(username)
						.password(password).login();
				Platform.runLater(() -> {
					loadingPane.setVisible(false);
					signInPane.setVisible(false);
					mainWindow.setVisible(true);
				});
			} catch (IGLoginException e) {
				Platform.runLater(() -> {
					if(e.getMessage().contains("The username you entered doesn't appear to belong to an account.")) {
						loginInvalidLabel.setText("Please check your username and try again.");
					} else {
						loginInvalidLabel.setText(e.getMessage());	
					}
					usernameField.setText("");
					passwordField.setText("");
					eyeIcon.setIconLiteral("fa-eye-slash");
					plaintextField.setVisible(false);
					;
					passwordField.setVisible(true);
				});
			}
		}).start();
	}

	@FXML
	private void loginButtonOnAction(ActionEvent event) {
		if (usernameField.getText().isEmpty() == false
				&& passwordField.getText().isEmpty() == false) {
			checkLogin(usernameField.getText(), passwordField.getText());
			startLoadingAnimation();
		} else {
			loginInvalidLabel.setText("");
		}
	}

	@FXML
	private void onEnterPressed(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER
				&& loginButton.isDisabled() == false) {
			usernameField.requestFocus();
			checkLogin(usernameField.getText(), passwordField.getText());
			startLoadingAnimation();
		} else {
			loginInvalidLabel.setText("");
		}
	}

	private void startLoadingAnimation() {
		signInPane.setDisable(true);
		loadingPane.setVisible(true);
		lockIcon.setVisible(false);
		userIcon.setVisible(false);
		RotateTransition rotation = new RotateTransition(Duration.seconds(3.5),
				loadingCircle);
		rotation.setByAngle(360);
		rotation.play();
		rotation.setOnFinished(event -> {
			loadingPane.setVisible(false);
			signInPane.setDisable(false);
			lockIcon.setVisible(true);
			userIcon.setVisible(true);
		});
	}

	@FXML
	private void onInputAction(ActionEvent event) {
		entryFailure.setText("");
		inputField.setText("");
		FileChooser curr = new FileChooser();
		curr.setTitle("Input Stream Browser");
		curr.getExtensionFilters()
				.addAll(new ExtensionFilter("Text Files", "*.txt"));
		File selectedFile = curr.showOpenDialog(null);
		if (selectedFile != null) {
			inputField.setText(selectedFile.getAbsolutePath());
		}
	}

	@FXML
	private void onOutputAction(ActionEvent event) {
		outputField.setText("");
		FileChooser curr = new FileChooser();
		curr.setTitle("Save Dialog");
		curr.getExtensionFilters().addAll(new ExtensionFilter(
				"Microsoft Excel Open XML Spreadsheet", "*.xlsx"));
		File selectedFile = curr.showSaveDialog(null);
		if (selectedFile != null) {
			outputField.setText(selectedFile.getAbsolutePath());
		}
	}

	private void settingsLoadingAnimation() {
		settingsPane.setDisable(true);
		settingsLoad.setVisible(true);
		RotateTransition rotation = new RotateTransition(Duration.seconds(1.5),
				settingsCircle);
		rotation.setByAngle(360);
		rotation.play();
		rotation.setOnFinished(event -> {
			settingsLoad.setVisible(false);
			settingsPane.setDisable(false);
		});
	}

	@FXML
	private void onSubmitAction(ActionEvent event) {
		if (entryField.getText().equals("")) {
			entryFailure.setText("Keyword cannot be empty.");
			entryField.setText("");
		} else {
			keywords.add(entryField.getText());
			entrySuccess.setText("Keyword added.");
			entryField.setText("");
			inputBrowse.setDisable(true);
		}
	}

	@FXML
	private void onInputEnterPressed(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {
			if (entryField.getText().equals("")) {
				entryFailure.setText("Keyword cannot be empty.");
				entryField.setText("");
			} else {
				keywords.add(entryField.getText());
				entrySuccess.setText("Keyword added.");
				settingsPane.requestFocus();
				entryField.setText("");
				inputBrowse.setDisable(true);
			}
		}
	}

	@FXML
	private void onSearchAction(ActionEvent event) {
		try {
			 results = client.actions().users().findByUsername(handleField.getText())
						.thenApply(userAction -> 
				            userAction.followersFeed().stream().flatMap(feedUsersResponse -> feedUsersResponse.getUsers().stream()).collect(Collectors.toList())
				               ).join();
			settingsLoadingAnimation();
			consolePane.setVisible(true);
		
			
	        OutputStream os = new TextAreaOutputStream(consoleLogs);
	        MyStaticOutputStreamAppender.setStaticOutputStream(os);
			
			for (Profile p : results) {
				p.get("biography");
				followersSet.add(p.getUsername());
	        }
			
			
			for (String follower : followersSet) {
					Thread.sleep(3000);
					String bio = client.actions().users().findByUsername(follower).get().getUser().getBiography();
					if (Arrays.stream(keywords.toArray(new String[0])).parallel().anyMatch(bio::contains)) {
						ArrayList<String> userInfo = new ArrayList<String>();
						userInfo.add(bio);
						Thread.sleep(3000);
						userInfo.add(client.actions().users().findByUsername(follower).get().getUser().getFollower_count() + "");
						Thread.sleep(3000);
						userInfo.add(client.actions().users().findByUsername(follower).get().getUser().getFull_name());
						extractedList.put(follower, userInfo);
					}
			}
			
			Workbook workbook = new XSSFWorkbook();
			Sheet spreadSheet = workbook.createSheet("Extracted handles");
			int rowNum = 0;
			for (String handle : extractedList.keySet()) {
				Row row = spreadSheet.createRow(rowNum++);
				int cellVal = 0;
				Cell cell = row.createCell(cellVal++);
				cell.setCellValue(handle);
				ArrayList<String> curr = extractedList.get(handle);
				for (String userInfo : curr) {
					cell = row.createCell(cellVal++);
					cell.setCellValue(userInfo);
				}
			}
			
			FileOutputStream out = new FileOutputStream(new File(outputField.getText()));
			workbook.write(out);
			workbook.close();
			out.close();
		} catch (Exception e) {
			if (e.getMessage().contains("User not found")) {
				settingsWarningLabel.setText("Invalid Instagram handle. Please retype and try again.");	
			} else {
				settingsWarningLabel.setText(e.getMessage());	
			}
			settingsLoadingAnimation();
		}
	}
	
	 private static class TextAreaOutputStream extends OutputStream {

	        private TextArea textArea;

	        public TextAreaOutputStream(TextArea textArea) {
	            this.textArea = textArea;
	        }

	        @Override
	        public void write(int b) throws IOException {
	            textArea.appendText(String.valueOf((char) b));
	        }
	    }

}
