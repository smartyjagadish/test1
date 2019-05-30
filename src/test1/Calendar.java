package test1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Calendar {
    private static String                        option;
    //private static Map<String, Boolean>          meetingRooms     = new HashMap<String, Boolean>();
    private static List<String>                  meetingRoomNames = new ArrayList<String>();
    public static List<String>                   employeeList     = new ArrayList<String>();
    public static Map<String, List<MeetingRoom>> empMeetingsMap   = new HashMap<String, List<MeetingRoom>>();
    private static BufferedReader                stdin            = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) {
        meetingRoomNames.add("M1");
        meetingRoomNames.add("M2");
        meetingRoomNames.add("M3");
        showCalendarOptions();
    }

    public static void showCalendarOptions() {
        try {
            int count = 0;
            boolean isExit = false;
            do {
                System.out.println("Enter the option");
                option = stdin.readLine();
                if ("exit".equals(option)) {
                    isExit = true;
                } else {
                    readOption(option);
                }
                count++;
            } while (!isExit && count < 100);
        } catch (Exception e) {
            System.out.println("Error at showing calendar options: " + e);
        }
    }

    public static void addEmployee(String name) {
        employeeList.add(name);
    }

    public static void printAllEmployeeNames() {
        for (String empName : employeeList) {
            System.out.println(empName);
        }
    }

    public static void readOption(String opt) throws Exception {
        int selOption = Integer.parseInt(opt);
        switch (selOption) {
        case 1:
            System.out.println("Add Employee Name:");
            addEmployee(stdin.readLine());
            return;
        case 2:
            System.out.println("Create meeting invite");
            System.out.println("Enter meeting name/description");
            String meetingName = stdin.readLine();
            for (int i = 0; i < employeeList.size(); i++) {
                System.out.println("Employee Name: " + employeeList.get(i) + " Employee Index: " + i);
            }

            boolean isInvitesCorrect = false;
            List<String> meetingInvites = null;
            do {
                meetingInvites = new ArrayList<String>();
                System.out.println("Add employee list with comma separated");
                String str = stdin.readLine();
                if (str != null) {
                    String[] empArray = str.split(",");
                    for (int i = 0; i < empArray.length; i++) {
                        try {
                            int empIndex = Integer.parseInt(empArray[i]);
                            meetingInvites.add(employeeList.get(empIndex));
                        } catch (Exception e) {
                            System.out.println("Please check the meeting invite format and try again");
                        }
                    }
                    isInvitesCorrect = true;
                }
            } while (!isInvitesCorrect);
            System.out.println("Start Time Format: dd/MM/yy hh:mm");
            String startTimeStr = stdin.readLine();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy hh:mm");
            Date startDate = sdf.parse(startTimeStr);
            System.out.println("End Time Format: dd/MM/yy hh:mm");
            Date endDate = sdf.parse(stdin.readLine());
            MeetingRoom mr = new MeetingRoom();
            mr.name = meetingName;
            mr.startTime = startDate;
            mr.endTime = endDate;
            for (String inviteName : meetingInvites) {
                if (empMeetingsMap.get(inviteName) == null) {
                    List<MeetingRoom> mrList = new ArrayList<MeetingRoom>();
                    mrList.add(mr);
                    empMeetingsMap.put(inviteName, mrList);
                } else {
                    List<MeetingRoom> mrList = empMeetingsMap.get(inviteName);
                    mrList.add(mr);
                    empMeetingsMap.put(inviteName, mrList);
                }
            }
            System.out.println("Meeting room added successfully");
            return;

        case 3:
            System.out.println("Employee List:");
            for (int i = 0; i < employeeList.size(); i++) {
                System.out.println("Employee Name: " + employeeList.get(0) + " Employee Index:" + i);
            }
            System.out.println("Please select employee index:");
            String empIndexStr = stdin.readLine();
            String employeeName = employeeList.get(Integer.parseInt(empIndexStr));
            System.out.println("List of meetings for this employee: " + employeeName);
            if (empMeetingsMap.get(employeeName) == null) {
                System.out.println("No meetings scheduled for this employee: " + employeeName);
            } else {
                int count = 1;
                for (MeetingRoom meetingRoom : empMeetingsMap.get(employeeName)) {
                    System.out.println("Meeting Number: " + count);
                    System.out.println("Meeting Name: " + meetingRoom.name);
                    System.out.println("Start Time: " + meetingRoom.startTime.toString());
                    System.out.println("End Time: " + meetingRoom.endTime.toString());
                    System.out.println("-------------------");
                    count++;
                }
            }
            return;
            

        default:
            return;
        }

    }
}
