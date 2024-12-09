package gui;

import  javax.swing.*;
import  java.awt.*;
import  java.awt.event.*;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static classes.Event.*;
import static classes.Booking.*;
import static classes.Customer.*;



import static db.dataBase.*;

public class GUI{
    JFrame frame = new JFrame("TicketNow");
    JPanel leftPanel = new JPanel();
    JPanel rightPanel = new JPanel();
    JButton addEventButton = new JButton("Add Event"); //1
    JButton deleteEventButton = new JButton("Delete Event"); //2
    JButton registrationButton = new JButton("Register new user");//3
    JButton deleteUserButton = new JButton("Delete Customer");//4
    JButton reserveButton = new JButton("Reserve tickets");//5
    JButton deleteBookingButton = new JButton("Delete Booking");//6
    JButton availableTicketsForEventButton = new JButton("Tickets Left");//7
    JButton additionalFeaturesButton = new JButton("Additional Features");//8

    private void initButtons(){
        addEventButton.setBounds(25, 10, 275, 90);
        deleteEventButton.setBounds(25, 120, 275, 90);
        registrationButton.setBounds(25, 230, 275, 90);
        deleteUserButton.setBounds(25, 340, 275, 90);
        reserveButton.setBounds(25, 450, 275, 90);
        deleteBookingButton.setBounds(25, 560, 275, 90);
        availableTicketsForEventButton.setBounds(25, 670, 275, 90);
        additionalFeaturesButton.setBounds(25, 780, 275, 90);


    }

    public void initPanels(JPanel left, JPanel right){
        left.setVisible(true);
        right.setVisible(true);
        left.setBackground(Color.RED);
        right.setBackground(Color.BLUE);
        left.setBounds(0, 0, 325, 900);
        right.setBounds(325, 0, 975, 900);
        left.setLayout(null);

        initButtons();

        left.add(addEventButton);
        left.add(deleteEventButton);
        left.add(registrationButton);
        left.add(deleteUserButton);
        left.add(reserveButton);
        left.add(deleteBookingButton);
        left.add(availableTicketsForEventButton);
        left.add(additionalFeaturesButton);

    }

    private void buttonActions(){
        addEventButton.addActionListener(new ActionListener() { /* ADD EVENT BUTTON ACTIONS ||1||*/
            public void actionPerformed(ActionEvent e) {
                rightPanel.removeAll();
                rightPanel.repaint();
                JTextField eventName = new JTextField();
                JTextField eventDate = new JTextField();
                JTextField eventTime = new JTextField();
                JTextField eventType = new JTextField();
                JTextField eventCapacity = new JTextField();
                JTextField vipTickets = new JTextField();
                JTextField regularTickets = new JTextField();
                JLabel eventNameLabel = new JLabel("Event Name");
                JLabel eventDateLabel = new JLabel("Event Date");
                JLabel eventTimeLabel = new JLabel("Event Time");
                JLabel eventTypeLabel = new JLabel("Event Type");
                JLabel eventCapacityLabel = new JLabel("Event Capacity");
                JLabel vipTicketsLabel = new JLabel("VIP Tickets");
                JLabel regularTicketsLabel = new JLabel("Regular Tickets");
                JLabel costLabel = new JLabel("Cost");
                JTextField cost = new JTextField();
                JButton submitButton = new JButton("Submit");
                eventNameLabel.setBounds(25, 10, 275, 90);
                eventDateLabel.setBounds(25, 135, 275, 90);
                eventTimeLabel.setBounds(25, 260, 275, 90);
                eventTypeLabel.setBounds(25, 385, 275, 90);
                eventCapacityLabel.setBounds(25, 510, 275, 90);
                eventName.setBounds(25, 85, 275, 40);
                eventDate.setBounds(25, 210, 275, 40);
                eventTime.setBounds(25, 335, 275, 40);
                eventType.setBounds(25, 460, 275, 40);
                eventCapacity.setBounds(25, 585, 275, 40);

                vipTicketsLabel.setBounds(400, 10, 275, 90);
                regularTicketsLabel.setBounds(400, 135, 275, 90);
                vipTickets.setBounds(400, 85, 275, 40);
                regularTickets.setBounds(400, 210, 275, 40);
                costLabel.setBounds(400, 250, 275, 90);
                cost.setBounds(400, 320, 275, 40);
                submitButton.setBounds(225, 700, 275, 90);

                //label editing
                eventNameLabel.setFont(new Font("Arial", Font.BOLD, 20));
                eventDateLabel.setFont(new Font("Arial", Font.BOLD, 20));
                eventTimeLabel.setFont(new Font("Arial", Font.BOLD, 20));
                eventTypeLabel.setFont(new Font("Arial", Font.BOLD, 20));
                eventCapacityLabel.setFont(new Font("Arial", Font.BOLD, 20));
                vipTicketsLabel.setFont(new Font("Arial", Font.BOLD, 20));
                regularTicketsLabel.setFont(new Font("Arial", Font.BOLD, 20));
                costLabel.setFont(new Font("Arial", Font.BOLD, 20));

                rightPanel.add(eventName);
                rightPanel.add(eventDate);
                rightPanel.add(eventTime);
                rightPanel.add(eventType);
                rightPanel.add(eventCapacity);
                rightPanel.add(submitButton);
                rightPanel.add(eventNameLabel);
                rightPanel.add(eventDateLabel);
                rightPanel.add(eventTimeLabel);
                rightPanel.add(eventTypeLabel);
                rightPanel.add(eventCapacityLabel);
                rightPanel.add(vipTickets);
                rightPanel.add(regularTickets);
                rightPanel.add(vipTicketsLabel);
                rightPanel.add(regularTicketsLabel);
                rightPanel.add(cost);
                rightPanel.add(costLabel);
                submitButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String strEventName = eventName.getText();
                        String strEventDateText = eventDate.getText();
                        String strEventTimeText = eventTime.getText();
                        String strEventTypeText = eventType.getText();
                        String strEventCapacityText = eventCapacity.getText();
                        String strVIPTickets = vipTickets.getText();
                        String strRegularTickets = regularTickets.getText();
                        String strCost = cost.getText();

                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        java.util.Date date1 = null;
                        try {
                            date1 = sdf.parse(strEventDateText);
                        } catch (ParseException ex) {
                            ex.printStackTrace();
                        }

                        assert date1 != null;
                        try {
                            if (addEvent(strEventName, new java.sql.Date(date1.getTime()), Time.valueOf(strEventTimeText), strEventTypeText, Integer.parseInt(strEventCapacityText), Integer.parseInt(strRegularTickets), Integer.parseInt(strVIPTickets), Float.parseFloat(strCost))) {
                                // clear all the text fields
                                eventName.setText("");
                                eventDate.setText("");
                                eventTime.setText("");
                                eventType.setText("");
                                eventCapacity.setText("");
                                vipTickets.setText("");
                                regularTickets.setText("");
                                cost.setText("");
                            }
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    }
                });
            }
        });

        deleteEventButton.addActionListener(new ActionListener() /* DELETE EVENT BUTTON ACTIONS ||2|| */{
            public void actionPerformed(ActionEvent e) {
                rightPanel.setLayout(null);
                rightPanel.removeAll();
                rightPanel.repaint();

                JLabel yourReservationsLabel = new JLabel("Events to Cancel");
                JComboBox<String> eventList = new JComboBox<>();
                String id_name;

                try {
                    ResultSet rs = get("SELECT * FROM events");
                    while (rs.next()) {
                        id_name = rs.getString("EventId") + " " + rs.getString("Name");
                        eventList.addItem(id_name);
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }


                JButton cancelEventButton = new JButton("Cancel Event");
                yourReservationsLabel.setBounds(25, 10, 275, 90);
                eventList.setBounds(25, 85, 275, 40);
                cancelEventButton.setBounds(25, 135, 200, 50);
                //label editing
                yourReservationsLabel.setFont(new Font("Arial", Font.BOLD, 20));
                rightPanel.add(eventList);
                rightPanel.add(cancelEventButton);
                rightPanel.add(yourReservationsLabel);
                rightPanel.revalidate();
                rightPanel.repaint();

                cancelEventButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        rightPanel.setLayout(null);
                        String eventToDelete = (String) eventList.getSelectedItem();
                        try {
                            assert eventToDelete != null;
                            if (deleteEvent(Integer.parseInt(eventToDelete.split(" ")[0]))) {
                                JLabel eventDeleteLabel = new JLabel("Event Deleted!");
                                eventDeleteLabel.setBounds(25, 170, 275, 90);
                                eventDeleteLabel.setFont(new Font("Arial", Font.BOLD, 20));
                                rightPanel.add(eventDeleteLabel);
                            }
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }


                        rightPanel.revalidate();
                        rightPanel.repaint();
                    }
                });
            }
        });

        registrationButton.addActionListener(new ActionListener() {/* REGISTRATION USER BUTTON ACTIONS ||3|| */
            public void actionPerformed(ActionEvent e) {
                rightPanel.removeAll();
                rightPanel.repaint();
                JTextField fullName = new JTextField();
                JTextField email = new JTextField();
                JTextField creditInfo = new JTextField();
                JLabel fullNameLabel = new JLabel("Full Name");
                JLabel emailLabel = new JLabel("Email");
                JLabel creditInfoLabel = new JLabel("Credit Card Info");
                JButton submitButton = new JButton("Submit");
                fullNameLabel.setBounds(25, 10, 275, 90);
                emailLabel.setBounds(25, 135, 275, 90);
                creditInfoLabel.setBounds(25, 260, 275, 90);
                fullName.setBounds(25, 85, 275, 40);
                email.setBounds(25, 210, 275, 40);
                creditInfo.setBounds(25, 335, 275, 40);
                submitButton.setBounds(25, 385, 275, 90);
                //label editing
                fullNameLabel.setFont(new Font("Arial", Font.BOLD, 20));
                emailLabel.setFont(new Font("Arial", Font.BOLD, 20));
                creditInfoLabel.setFont(new Font("Arial", Font.BOLD, 20));


                rightPanel.add(fullName);
                rightPanel.add(email);
                rightPanel.add(creditInfo);
                rightPanel.add(submitButton);
                rightPanel.add(fullNameLabel);
                rightPanel.add(emailLabel);
                rightPanel.add(creditInfoLabel);
                submitButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String creditInfoText = creditInfo.getText();
                        String emailText = email.getText();
                        String fullNameText = fullName.getText();

                        try {
                            if (addCustomer(fullNameText, emailText, creditInfoText)) {
                                // clear all the text fields
                                fullName.setText("");
                                email.setText("");
                                creditInfo.setText("");
                            }
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    }
                });
            }
        });

        deleteUserButton.addActionListener(new ActionListener() /* DELETE USER BUTTON ACTIONS ||4|| */{
            @Override
            public void actionPerformed(ActionEvent e) {
                rightPanel.setLayout(null);
                rightPanel.removeAll();
                rightPanel.repaint();
                JLabel yourReservationsLabel = new JLabel("Users to Delete");
                JComboBox<String> userList = new JComboBox<>();
                String id_name;

                try {
                    ResultSet rs = get("SELECT * FROM customers");
                    while (rs.next()) {
                        id_name = rs.getString("CustomerId") + " " + rs.getString("FullName");
                        userList.addItem(id_name);
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

                JButton deleteUserButton = new JButton("Delete User");
                yourReservationsLabel.setBounds(25, 10, 275, 90);
                userList.setBounds(25, 85, 275, 40);
                deleteUserButton.setBounds(25, 135, 200, 50);
                //label editing
                yourReservationsLabel.setFont(new Font("Arial", Font.BOLD, 20));
                rightPanel.add(userList);
                rightPanel.add(deleteUserButton);
                rightPanel.add(yourReservationsLabel);
                rightPanel.revalidate();
                rightPanel.repaint();

                deleteUserButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        rightPanel.setLayout(null);
                        String userToDelete = (String) userList.getSelectedItem();
                        try {
                            assert userToDelete != null;
                            if (deleteCustomer(Integer.parseInt(userToDelete.split(" ")[0]))) {
                                userList.removeItemAt(userList.getSelectedIndex());
                            }
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }

                        rightPanel.revalidate();
                        rightPanel.repaint();
                    }
                });
            }
        });

        reserveButton.addActionListener(new ActionListener() /* RESERVE TICKETS BUTTON ACTIONS ||5|| */{
            public void actionPerformed(ActionEvent e) {
                rightPanel.setLayout(null);
                rightPanel.removeAll();
                rightPanel.repaint();

                // choosing customer
                JLabel clientLabel = new JLabel("Chose Client");
                JComboBox<String> clientList = new JComboBox<>();
                String id_name;
                try {
                    ResultSet rs = get("SELECT * FROM customers");
                    while (rs.next()) {
                        id_name = rs.getString("CustomerId") + " " + rs.getString("FullName");
                        clientList.addItem(id_name);
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

                JLabel eventLabel = new JLabel("Chose Event");
                JComboBox<String> eventList = new JComboBox<>();
                try {
                    ResultSet rs = get("SELECT * FROM events");
                    while (rs.next()) {
                        id_name = rs.getString("EventId") + " " + rs.getString("Name");
                        eventList.addItem(id_name);
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

                JLabel dateLabel = new JLabel("Booking Date");
                JTextField bookingDate = new JTextField();

                JButton submitButton = new JButton("Submit");
                clientLabel.setBounds(25, 10, 275, 90);
                clientList.setBounds(25, 85, 275, 40);
                eventLabel.setBounds(25, 135, 275, 90);
                eventList.setBounds(25, 210, 275, 40);
                bookingDate.setBounds(25, 335, 275, 40);
                submitButton.setBounds(25, 385, 200, 50);
                dateLabel.setBounds(25, 260, 275, 90);
                //label editing
                clientLabel.setFont(new Font("Arial", Font.BOLD, 20));
                eventLabel.setFont(new Font("Arial", Font.BOLD, 20));
                rightPanel.add(submitButton);
                rightPanel.add(clientList);
                rightPanel.add(eventList);
                rightPanel.add(bookingDate);
                rightPanel.add(clientLabel);
                rightPanel.add(eventLabel);
                rightPanel.add(dateLabel);
                rightPanel.revalidate();
                rightPanel.repaint();

                submitButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        rightPanel.removeAll();
                        rightPanel.setLayout(null);
                        int eventSelected = Integer.parseInt(eventList.getSelectedItem().toString().split(" ")[0]);
                        int clientSelected = Integer.parseInt(clientList.getSelectedItem().toString().split(" ")[0]);
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        java.util.Date bookingDateSTR = null;

                        try {
                            bookingDateSTR = sdf.parse(bookingDate.getText());
                        } catch (ParseException ex) {
                            throw new RuntimeException(ex);
                        }


                        // show label for the tickets and textboxes to get the ticketVIP and ticketRegular
                        JLabel ticketVIPLabel = null;
                        try {
                            ticketVIPLabel = new JLabel("VIP Tickets available:" + availableTickets(eventSelected, 2));
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                        JTextField ticketVIP = new JTextField();


                        JLabel ticketRegularLabel = null;
                        try {
                            ticketRegularLabel = new JLabel("Regular Tickets available:" + availableTickets(eventSelected, 1));
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                        JTextField ticketRegular = new JTextField();
                        JButton submitButton2 = new JButton("Submit");
                        ticketVIPLabel.setBounds(25, 10, 275, 90);
                        ticketVIP.setBounds(25, 85, 275, 40);
                        ticketRegularLabel.setBounds(25, 135, 275, 90);
                        ticketRegular.setBounds(25, 210, 275, 40);
                        submitButton2.setBounds(25, 260, 200, 50);
                        //label editing
                        ticketVIPLabel.setFont(new Font("Arial", Font.BOLD, 20));
                        ticketRegularLabel.setFont(new Font("Arial", Font.BOLD, 20));
                        rightPanel.add(ticketVIP);
                        rightPanel.add(ticketRegular);
                        rightPanel.add(submitButton2);
                        rightPanel.add(ticketVIPLabel);
                        rightPanel.add(ticketRegularLabel);
                        rightPanel.revalidate();
                        rightPanel.repaint();

                        java.util.Date finalBookingDateSTR = bookingDateSTR;
                        submitButton2.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                rightPanel.setLayout(null);
                                String ticketVIPSTR = ticketVIP.getText();
                                String ticketRegularSTR = ticketRegular.getText();

                                try {
                                    if (addBooking(clientSelected, new java.sql.Date(finalBookingDateSTR.getTime()), eventSelected, Integer.parseInt(ticketRegularSTR), Integer.parseInt(ticketVIPSTR))) {
                                        // clear all the text fields
                                        ticketVIP.setText("");
                                        ticketRegular.setText("");
                                    }
                                } catch (SQLException ex) {
                                    throw new RuntimeException(ex);
                                }

                                rightPanel.revalidate();
                                rightPanel.repaint();
                            }
                        });
                    }
                });
            }

        });

        deleteBookingButton.addActionListener(new ActionListener() /* DELETE BOOKING BUTTON ACTIONS ||6|| */{
            public void actionPerformed(ActionEvent e) {
                rightPanel.setLayout(null);
                rightPanel.removeAll();
                rightPanel.repaint();
                JLabel clientLabel = new JLabel("Chose Client");
                JComboBox<String> clientList = new JComboBox<>();
                String id_name;
                try {
                    ResultSet rs = get("SELECT * FROM customers");
                    while (rs.next()) {
                        id_name = rs.getString("CustomerId") + " " + rs.getString("FullName");
                        clientList.addItem(id_name);
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                JButton submitButton = new JButton("Submit");
                clientLabel.setBounds(25, 10, 275, 90);
                clientList.setBounds(25, 85, 275, 40);
                submitButton.setBounds(25, 135, 200, 50);
                //label editing
                clientLabel.setFont(new Font("Arial", Font.BOLD, 20));
                rightPanel.add(clientList);
                rightPanel.add(submitButton);
                rightPanel.add(clientLabel);
                rightPanel.revalidate();
                rightPanel.repaint();

                submitButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        rightPanel.setLayout(null);
                        String clientSTR = (String) clientList.getSelectedItem();
                        JLabel bookingLabel = new JLabel("Chose Booking");
                        JComboBox<String> bookingList = new JComboBox<>();
                        String id_name;
                        try {
                            assert clientSTR != null;
                            ResultSet rs = get("SELECT * FROM bookings WHERE CustomerId = " + Integer.parseInt(clientSTR.split(" ")[0]));
                            while (rs.next()) {
                                id_name = rs.getString("BookingId") + " " + rs.getString("BookingDate");
                                bookingList.addItem(id_name);
                            }
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                        JButton submitButton2 = new JButton("Submit");
                        bookingLabel.setBounds(25, 200, 275, 90);
                        bookingList.setBounds(25, 275, 275, 40);
                        submitButton2.setBounds(25, 325, 200, 50);
                        //label editing
                        bookingLabel.setFont(new Font("Arial", Font.BOLD, 20));
                        rightPanel.add(bookingList);
                        rightPanel.add(submitButton2);
                        rightPanel.add(bookingLabel);
                        rightPanel.revalidate();
                        rightPanel.repaint();

                        submitButton2.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                rightPanel.setLayout(null);
                                //edw kanei delte to booking
                                String bookingToDeleteSTR = (String) bookingList.getSelectedItem();
                                try {
                                    assert bookingToDeleteSTR != null;
                                    if (deleteBooking(Integer.parseInt(bookingToDeleteSTR.split(" ")[0]))) {
                                        bookingList.removeItemAt(bookingList.getSelectedIndex());
                                    }
                                } catch (SQLException ex) {
                                    throw new RuntimeException(ex);
                                }
                                rightPanel.revalidate();
                                rightPanel.repaint();
                            }
                        });
                    }
                });



            }
        });

        availableTicketsForEventButton.addActionListener(new ActionListener() /*AVAILABLE TICKETS FOR EVENT BUTTON ||7||*/{
            public void actionPerformed(ActionEvent e) {
                rightPanel.setLayout(null);
                rightPanel.removeAll();
                rightPanel.repaint();
                JLabel eventNameLabel = new JLabel("Chose Event");
                JComboBox<String> eventList = new JComboBox<>();
                String id_name;
                try {
                    ResultSet rs = get("SELECT * FROM events");
                    while (rs.next()) {
                        id_name = rs.getString("EventId") + " " + rs.getString("Name");
                        eventList.addItem(id_name);
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                JButton submitButton = new JButton("Submit");
                eventNameLabel.setBounds(25, 10, 275, 90);
                eventList.setBounds(25, 85, 275, 40);
                submitButton.setBounds(25, 135, 200, 50);
                //label editing
                eventNameLabel.setFont(new Font("Arial", Font.BOLD, 20));
                rightPanel.add(eventList);
                rightPanel.add(submitButton);
                rightPanel.add(eventNameLabel);
                rightPanel.revalidate();
                rightPanel.repaint();
                submitButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        rightPanel.setLayout(null);
                        String selectedEvent = (String) eventList.getSelectedItem(); //TODO! FIX THE STRINGS HERE
                        System.out.println(selectedEvent);
                        JLabel seatTypeLabel = new JLabel("Chose Seat Type");
                        String[] seatTypes = {"VIP", "Regular"};
                        JComboBox<String> seatTypeList = new JComboBox<>(seatTypes);
                        JLabel showTicketsLeft = null;
                        try {
                            assert selectedEvent != null;
                            showTicketsLeft = new JLabel("Tickets Left: " + availableTickets(Integer.parseInt(selectedEvent.split(" ")[0]), seatTypeList.getSelectedIndex() == 0 ? 2 : 1));
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                        JButton checkButton = new JButton("Check for tickets");
                        seatTypeLabel.setBounds(25, 200, 275, 90);
                        seatTypeList.setBounds(25, 275, 275, 40);
                        checkButton.setBounds(25, 325, 200, 50);

                        //label editing
                        seatTypeLabel.setFont(new Font("Arial", Font.BOLD, 20));
                        showTicketsLeft.setFont(new Font("Arial", Font.BOLD, 20));
                        rightPanel.add(seatTypeList);
                        rightPanel.add(checkButton);
                        rightPanel.add(seatTypeLabel);
                        rightPanel.add(showTicketsLeft);
                        rightPanel.revalidate();
                        rightPanel.repaint();

                        JLabel finalShowTicketsLeft = showTicketsLeft;
                        checkButton.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                rightPanel.setLayout(null);
                                finalShowTicketsLeft.setBounds(25, 400, 275, 90);
                                String selectedSeatType = (String) seatTypeList.getSelectedItem(); // Get selected seat type
                                System.out.println("Selected Seat Type: " + selectedSeatType);
                                int ticketsLeft = 20; // You can replace this with actual logic
                                finalShowTicketsLeft.setText("Tickets Left: " + ticketsLeft);
                                rightPanel.revalidate();
                                rightPanel.repaint();
                            }
                        });
                    }
                });
            }
        });

        additionalFeaturesButton.addActionListener(new ActionListener() /* ADDITIONAL FEATURES BUTTON ||8|| */{
            public void actionPerformed(ActionEvent e) {
                rightPanel.setLayout(null);
                rightPanel.removeAll();
                rightPanel.repaint();

                // Income for event feature ||9||
                JLabel incomeOfEventLabel = new JLabel("Income of Event");

                JComboBox<String> eventList = new JComboBox<>();
                String id_name;
                try {
                    ResultSet rs = get("SELECT * FROM events");
                    while (rs.next()) {
                        id_name = rs.getString("EventId") + " " + rs.getString("Name");
                        eventList.addItem(id_name);
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

                JButton submitButton = new JButton("Submit");
                String event = (String) eventList.getSelectedItem();
                float income = 0;
                try {
                    assert event != null;
                    income = eventIncome(Integer.parseInt(event.split(" ")[0]));
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                incomeOfEventLabel.setBounds(25, 10, 275, 90);
                eventList.setBounds(25, 85, 275, 40);
                submitButton.setBounds(25, 135, 200, 50);
                //label editing
                incomeOfEventLabel.setFont(new Font("Arial", Font.BOLD, 20));
                rightPanel.add(eventList);
                rightPanel.add(submitButton);
                rightPanel.add(incomeOfEventLabel);
                rightPanel.revalidate();
                rightPanel.repaint();

                float finalIncome = income;
                submitButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        rightPanel.setLayout(null);
                        JLabel incomeLabel = new JLabel("Income: " + finalIncome);
                        int income = 2000; // You can replace this with actual logic
                        JLabel incomeValue = new JLabel("" + income);
                        incomeLabel.setBounds(25, 180, 275, 90);
                        incomeValue.setBounds(25, 240, 275, 40);
                        //label editing
                        incomeLabel.setFont(new Font("Arial", Font.BOLD, 20));
                        incomeValue.setFont(new Font("Arial", Font.BOLD, 20));
                        rightPanel.add(incomeLabel);
                        rightPanel.add(incomeValue);
                        rightPanel.revalidate();
                        rightPanel.repaint();
                    }
                });

                //most popular event based on booking feature ||10||
                JLabel mostPopularEventLabel = new JLabel("Most Popular Event is:");
                JButton submitButtonForEvent = new JButton("Submit");
                mostPopularEventLabel.setBounds(25, 300, 330, 90);
                submitButtonForEvent.setBounds(25, 430, 200, 50);


                //label editing
                mostPopularEventLabel.setFont(new Font("Arial", Font.BOLD, 20));
                rightPanel.add(mostPopularEventLabel);
                rightPanel.add(submitButtonForEvent);
                rightPanel.revalidate();
                rightPanel.repaint();

                submitButtonForEvent.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        rightPanel.setLayout(null);
                        String mostPopularEvent; // You can replace this with actual logic
                        try {
                            mostPopularEvent = mostPopularEvent();
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                        JLabel mostPopularEventValue = new JLabel(mostPopularEvent);
                        mostPopularEventValue.setBounds(25, 350, 275, 90);
                        mostPopularEventValue.setFont(new Font("Arial", Font.BOLD, 20));
                        rightPanel.add(mostPopularEventValue);
                        rightPanel.revalidate();
                        rightPanel.repaint();
                    }
                });

                // ||||11||||
                JLabel dateStartLabel = new JLabel("Date start");
                JTextField dateStart = new JTextField();
                JLabel dateEndLabel = new JLabel("Date end");
                JTextField dateEnd = new JTextField();
                JButton submitButtonForDate = new JButton("Submit");

                dateStartLabel.setBounds(25, 500, 275, 90);
                dateStart.setBounds(25, 570, 275, 40);
                dateEndLabel.setBounds(25, 600, 275, 90);
                dateEnd.setBounds(25, 670, 275, 40);
                submitButtonForDate.setBounds(25, 730, 200, 50);
                //label editing
                dateStartLabel.setFont(new Font("Arial", Font.BOLD, 20));
                dateEndLabel.setFont(new Font("Arial", Font.BOLD, 20));
                rightPanel.add(dateStart);
                rightPanel.add(dateEnd);
                rightPanel.add(submitButtonForDate);
                rightPanel.add(dateStartLabel);
                rightPanel.add(dateEndLabel);

                submitButtonForDate.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String info;
                        try {
                            info = mostProfitableEvent(Date.valueOf(dateStart.getText()), Date.valueOf(dateEnd.getText()));
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                        JLabel messageLabel = new JLabel(info);
                        messageLabel.setBounds(25, 770, 275, 90);
                        messageLabel.setFont(new Font("Arial", Font.BOLD, 20));
                        rightPanel.add(messageLabel);

                        rightPanel.revalidate();
                        rightPanel.repaint();
                    }
                });

                // ||||12||||
                rightPanel.setLayout(null);
                JLabel eventLabel = new JLabel("Chose Event");
                JComboBox<String> eventLista = new JComboBox<>();
                eventLista.addItem("All");
                try {
                    ResultSet rs = get("SELECT * FROM events");
                    while (rs.next()) {
                        id_name = rs.getString("EventId") + " " + rs.getString("Name");
                        eventLista.addItem(id_name);
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                JLabel ticketTypeLabel = new JLabel("Chose Ticket Type");
                String[] ticketTypes = {"Regular", "VIP"};
                JComboBox<String> ticketTypeList = new JComboBox<>(ticketTypes);
                JButton submitButton22 = new JButton("Submit");
                eventLabel.setBounds(500, 10, 275, 90);
                eventLista.setBounds(500, 85, 275, 40);
                ticketTypeLabel.setBounds(500, 135, 275, 90);
                ticketTypeList.setBounds(500, 210, 275, 40);
                submitButton22.setBounds(500, 260, 200, 50);

                //label editing
                eventLabel.setFont(new Font("Arial", Font.BOLD, 20));
                ticketTypeLabel.setFont(new Font("Arial", Font.BOLD, 20));
                rightPanel.add(ticketTypeList);
                rightPanel.add(ticketTypeLabel);
                rightPanel.add(eventLista);
                rightPanel.add(submitButton22);
                rightPanel.add(eventLabel);
                rightPanel.revalidate();
                rightPanel.repaint();

                submitButton22.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        float[] profits = new float[2];
                        String event = (String) eventLista.getSelectedItem();
                        if (eventLista.getSelectedIndex() == 0) {
                            try {
                                profits = profitsTickets(0);
                            } catch (SQLException ex) {
                                throw new RuntimeException(ex);
                            }
                        } else {
                            try {
                                profits = profitsTickets(Integer.parseInt(event.split(" ")[0]));
                            } catch (SQLException ex) {
                                throw new RuntimeException(ex);
                            }
                        }
                        rightPanel.setLayout(null);
                        JLabel messageLabel = new JLabel("The event profits are: "+ (ticketTypeList.getSelectedIndex() == 0 ? profits[0] : profits[1]));
                        messageLabel.setBounds(500, 325, 275, 90);
                        messageLabel.setFont(new Font("Arial", Font.BOLD, 20));
                        rightPanel.add(messageLabel);
                        rightPanel.revalidate();
                        rightPanel.repaint();
                    }
                });



            }
        });
    }


    GUI (){
        frame.setLayout(null);
        frame.add(leftPanel);
        frame.add(rightPanel);
        initPanels(leftPanel, rightPanel);
        frame.setSize(1300, 920);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        buttonActions();
    }



    public static void main(String[] args){
        createDb();
        initDb();
        GUI gui = new GUI();

    }
}
