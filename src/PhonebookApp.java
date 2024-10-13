import javax.swing.*; // Import Swing components
import java.awt.*; // Import AWT components for layout
import java.awt.event.ActionEvent; // Import for handling action events
import java.awt.event.ActionListener; // Import for action listener interface
import java.util.ArrayList; // Import ArrayList
import java.util.Comparator; // Import Comparator
import java.util.List; // Import List

// Class to represent a contact with a name and phone number
class Contact {
    String name;
    String phoneNumber;

    // Constructor to initialize a contact
    Contact(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }
}

// Main class for the phonebook application
public class PhonebookApp {
    private List<Contact> phonebook; // List to store contacts
    private JFrame frame; // Main application window
    private JTextArea outputArea; // Text area for output display

    // Constructor to initialize the phonebook and GUI components
    public PhonebookApp() {
        phonebook = new ArrayList<>();
        createGUI(); // Set up the GUI
    }

    // Main method to start the application
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PhonebookApp()); // Launch the GUI
    }

    // Method to create the GUI
    private void createGUI() {
        frame = new JFrame("Phonebook App"); // Create main frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close program on exit
        frame.setSize(400, 400); // Set frame size
        frame.setLayout(new BorderLayout()); // Use BorderLayout for the frame

        // Create output area
        outputArea = new JTextArea();
        outputArea.setEditable(false); // Make output area read-only
        frame.add(new JScrollPane(outputArea), BorderLayout.CENTER); // Add output area with scroll

        // Create panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(0, 1)); // Vertical arrangement

        // Create buttons and add action listeners
        String[] buttonLabels = {
                "Insert Contact",
                "Search Contact",
                "Display All Contacts",
                "Delete Contact",
                "Update Contact",
                "Sort Contacts",
                "Analyze Search Efficiency",
                "Exit"
        };

        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.addActionListener(new ButtonClickListener(label)); // Add action listener
            buttonPanel.add(button); // Add button to panel
        }

        // Add button panel to the frame
        frame.add(buttonPanel, BorderLayout.WEST); // Add buttons to the left side

        // Set frame visibility
        frame.setVisible(true); // Show the frame
    }

    // Action listener for button clicks
    private class ButtonClickListener implements ActionListener {
        private String action; // Action corresponding to the button

        ButtonClickListener(String action) {
            this.action = action;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            switch (action) {
                case "Insert Contact":
                    insertContact(); // Insert a new contact
                    break;
                case "Search Contact":
                    searchContact(); // Search for a contact
                    break;
                case "Display All Contacts":
                    displayAllContacts(); // Display all contacts
                    break;
                case "Delete Contact":
                    deleteContact(); // Delete a contact
                    break;
                case "Update Contact":
                    updateContact(); // Update a contact's phone number
                    break;
                case "Sort Contacts":
                    sortContacts(); // Sort contacts by name
                    break;
                case "Analyze Search Efficiency":
                    analyzeSearchEfficiency(); // Analyze search efficiency
                    break;
                case "Exit":
                    System.exit(0); // Exit the application
                    break;
            }
        }
    }

    // Method to insert a new contact
    private void insertContact() {
        String name = JOptionPane.showInputDialog(frame, "Enter name:"); // Prompt for name
        if (name == null || name.isEmpty()) {
            outputArea.append("Name cannot be empty.\n"); // Check for empty name
            return;
        }
        String phoneNumber = JOptionPane.showInputDialog(frame, "Enter phone number:"); // Prompt for phone number
        phonebook.add(new Contact(name, phoneNumber)); // Add new contact to the phonebook
        outputArea.append("Contact added: " + name + "\n");
    }

    // Method to search for a contact by name
    private void searchContact() {
        String name = JOptionPane.showInputDialog(frame, "Enter name to search:"); // Prompt for name to search
        Contact result = searchContactByName(name); // Search for the contact
        if (result != null) {
            outputArea.append("Contact found: Name = " + result.name + ", Phone Number = " + result.phoneNumber + "\n");
        } else {
            outputArea.append("Contact not found.\n"); // If not found, notify the user
        }
    }

    // Method to display all contacts in the phonebook
    private void displayAllContacts() {
        if (phonebook.isEmpty()) {
            outputArea.append("Phonebook is empty.\n"); // Check if phonebook is empty
        } else {
            // Iterate and display each contact
            String contacts = "";
            for (Contact contact : phonebook) {
                contacts = contacts + contact.name + ": " + contact.phoneNumber + "\n";
            }
            outputArea.setText(contacts);
        }
    }

    // Method to delete a contact by name
    private void deleteContact() {
        String name = JOptionPane.showInputDialog(frame, "Enter name to delete:"); // Prompt for name to delete
        Contact contactToRemove = searchContactByName(name); // Search for the contact
        if (contactToRemove != null) {
            phonebook.remove(contactToRemove); // Remove the contact if found
            outputArea.append("Contact deleted: " + name + "\n");
        } else {
            outputArea.append("Contact not found.\n"); // Notify if not found
        }
    }

    // Method to update a contact's phone number
    private void updateContact() {
        String name = JOptionPane.showInputDialog(frame, "Enter name to update:"); // Prompt for name to update
        Contact contactToUpdate = searchContactByName(name); // Search for the contact
        if (contactToUpdate != null) {
            String newPhoneNumber = JOptionPane.showInputDialog(frame, "Enter new phone number:"); // Prompt for new phone number
            contactToUpdate.phoneNumber = newPhoneNumber; // Update the phone number
            outputArea.append("Contact updated: " + name + "\n");
        } else {
            outputArea.append("Contact not found.\n"); // Notify if not found
        }
    }

    // Method to sort contacts alphabetically by name
    private void sortContacts() {
        phonebook.sort(Comparator.comparing(contact -> contact.name)); // Sort contacts
        outputArea.append("Contacts sorted.\n");
    }

    // Method to analyze search efficiency
    private void analyzeSearchEfficiency() {
        outputArea.append("Search Time Complexity: O(n)\n"); // Inform user about time complexity
    }

    // Helper method to search for a contact by name
    private Contact searchContactByName(String name) {
        return phonebook.stream()
                .filter(contact -> contact.name.equalsIgnoreCase(name)) // Search for contact
                .findFirst()
                .orElse(null);
    }
}
