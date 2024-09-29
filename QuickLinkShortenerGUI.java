import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class QuickLinkShortenerGUI extends JFrame {
    private Map<String, String> urlMap;
    private JTextField longUrlField;
    private JTextField shortUrlField;
    private JTextArea outputArea;
    private static final String BASE_URL = "http://short.ly/";

    public QuickLinkShortenerGUI() {
        urlMap = new HashMap<>();
        setupUI();
    }

    private void setupUI() {
        setTitle("QuickLink Shortener");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(3, 2));

        inputPanel.add(new JLabel("Long URL:"));
        longUrlField = new JTextField();
        inputPanel.add(longUrlField);

        inputPanel.add(new JLabel("Shortened URL:"));
        shortUrlField = new JTextField();
        inputPanel.add(shortUrlField);

        JButton shortenButton = new JButton("Shorten URL");
        shortenButton.addActionListener(new ShortenUrlListener());
        inputPanel.add(shortenButton);

        JButton retrieveButton = new JButton("Retrieve Original URL");
        retrieveButton.addActionListener(new RetrieveUrlListener());
        inputPanel.add(retrieveButton);

        add(inputPanel, BorderLayout.NORTH);

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        add(new JScrollPane(outputArea), BorderLayout.CENTER);

        setVisible(true);
    }

    private class ShortenUrlListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String longUrl = longUrlField.getText();
            String shortUrl = BASE_URL + Integer.toHexString(longUrl.hashCode());
            urlMap.put(shortUrl, longUrl);
            outputArea.append("Shortened URL: " + shortUrl + "\n");
            longUrlField.setText("");
        }
    }

    private class RetrieveUrlListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String shortUrl = shortUrlField.getText();
            String originalUrl = urlMap.get(shortUrl);
            if (originalUrl != null) {
                outputArea.append("Original URL: " + originalUrl + "\n");
            } else {
                outputArea.append("Shortened URL not found.\n");
            }
            shortUrlField.setText("");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(QuickLinkShortenerGUI::new);
    }
}
