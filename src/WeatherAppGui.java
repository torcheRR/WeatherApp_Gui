import org.json.simple.JSONObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;

public class WeatherAppGui extends JFrame {
    private JSONObject weatherData;

    public WeatherAppGui() {
        //setup  our gui and a title
        super("Weather App");

        //configure gui to end the program's process once it has been closed
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        //set the size of our gui(in pixels)
        setSize(450, 650);

        //load our gui the center of our screen
        setLocationRelativeTo(null);

        //make our layout manager null to manually position our components withing the gui
        setLayout(null);

        //prevent any resize of our gui
        setResizable(false);

        //add components
        addGuiComponents();
    }

    private void addGuiComponents() {
        //search field
        JTextField searchTextField = new JTextField();

        //set the location and size of our components
        searchTextField.setBounds(15, 13, 351, 49);

        //change the font size and style
        searchTextField.setFont(new Font("Dialog", Font.PLAIN, 24));

        //add search text field
        add(searchTextField);

        //weather image
        JLabel weatherConditionImage = new JLabel(loadImage("WeatherApp_Gui/src/assets/cloudy.png"));
        weatherConditionImage.setBounds(0, 125, 450, 217);

        //add image
        add(weatherConditionImage);

        //temprature text
        JLabel tempratureText = new JLabel("10 C");
        tempratureText.setBounds(0, 350, 450, 54);
        tempratureText.setFont(new Font("Dialog", Font.BOLD, 48));

        //center the text
        tempratureText.setHorizontalAlignment(SwingConstants.CENTER);

        //add temp text
        add(tempratureText);

        //weather condition description
        JLabel weatherConditionDesc = new JLabel("Cloudy");
        weatherConditionDesc.setBounds(0, 405, 450, 36);
        weatherConditionDesc.setFont(new Font("Dialog", Font.PLAIN, 32));
        weatherConditionDesc.setHorizontalAlignment(SwingConstants.CENTER);

        //add weather desc
        add(weatherConditionDesc);

        //humidity image
        JLabel humidityImage = new JLabel(loadImage("WeatherApp_Gui/src/assets/humidity.png"));
        humidityImage.setBounds(15, 500, 74, 66);

        //add humidity image
        add(humidityImage);

        //humidity text
        JLabel humidityText = new JLabel("<html><b>Humidity</b> 100%</html>");
        humidityText.setBounds(90, 500, 85, 55);
        humidityText.setFont(new Font("Dialog", Font.PLAIN, 16));

        //add humidity text
        add(humidityText);

        //windspeed image
        JLabel windSpeedImage = new JLabel(loadImage("WeatherApp_Gui/src/assets/windspeed.png"));
        windSpeedImage.setBounds(220, 500, 74, 66);

        //add windSpeed image
        add(windSpeedImage);

        // windSpeed text
        JLabel windSpeedText = new JLabel("<html><b>Windspeed</b> 15km/h</html>");
        windSpeedText.setBounds(310, 500, 90, 55);
        windSpeedText.setFont(new Font("Dialog", Font.PLAIN, 16));

        //add windspeed text
        add(windSpeedText);

        //search button
        JButton searchButton = new JButton(loadImage("WeatherApp_Gui/src/assets/search.png"));

        //change the cursor to a hand cursor when hovering over this button
        searchButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        searchButton.setBounds(375, 13, 49, 49);
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //get location from user
                String userInput = searchTextField.getText();

                //validate input - remove whitespace to ensure non-empty text
                if (userInput.replaceAll("\\s", "").length() <= 0) {
                    return;
                }

                //retrieve weather data
                weatherData = WeatherApp.getWeatherData(userInput);

                //update gui

                //update weather image
                String weatherCondition = (String) weatherData.get("weather_condition");

                //depending on the condition, we will update the weather image that corresponds with the condition
                switch (weatherCondition) {
                    case "Clear":
                        weatherConditionImage.setIcon(loadImage("WeatherApp_Gui/src/assets/clear.png"));
                        break;
                    case "Cloudy":
                        weatherConditionImage.setIcon(loadImage("WeatherApp_Gui/src/assets/cloudy.png"));
                        break;
                    case "Rain":
                        weatherConditionImage.setIcon(loadImage("WeatherApp_Gui/src/assets/rain.png"));
                        break;
                    case "Snow":
                        weatherConditionImage.setIcon(loadImage("WeatherApp_Gui/src/assets/snow.png"));
                        break;
                }

                //update temperature text
                double temperature = (double) weatherData.get("temperature");
                tempratureText.setText(temperature + " C");

                //update weather condition text
                weatherConditionDesc.setText(weatherCondition);

                //update humidity text
                long humidity = (long) weatherData.get("humidity");
                humidityText.setText("<html><b>Humidity</b> " + humidity + "%</html>");

                //update windspeed text
                double windspeed = (double) weatherData.get("windspeed");
                windSpeedText.setText("<html><b>Windspeed</b> " + windspeed + "%</html>");

            }
        });
        searchButton.setBackground(null);
        searchButton.setBorderPainted(false);

        //add search button
        add(searchButton);
    }

    //used to create imgs in our gui components
    private ImageIcon loadImage(String resourcePath) {
        try {
            //read the image file from given path
            BufferedImage image = ImageIO.read(new File(resourcePath));

            //returns an image icon so that our components can render it
            return new ImageIcon(image);
        } catch (Exception e) {
            e.printStackTrace();
        }


        System.out.println("Could not found resource");
        return null;
    }
}
