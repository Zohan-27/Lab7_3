import java.io.*;
import java.util.Scanner;

interface Displayable {
    void displayInfo();
}

abstract class Visualization implements Displayable, Serializable {
    private int width;
    protected int height;
    String backgroundColor;
    public boolean isInteractive;

    public Visualization(int width, int height, String backgroundColor, boolean isInteractive) {
        this.width = width;
        this.height = height;
        this.backgroundColor = backgroundColor;
        this.isInteractive = isInteractive;
    }

    public int getWidth() {
        return width;
    }
}

class VisualizationFrame extends Visualization implements Serializable {
    private String frameType;
    protected boolean isResizable;
    public int zIndex;

    public VisualizationFrame(int width, int height, String backgroundColor, boolean isInteractive,
                              String frameType, boolean isResizable, int zIndex) {
        super(width, height, backgroundColor, isInteractive);
        this.frameType = frameType;
        this.isResizable = isResizable;
        this.zIndex = zIndex;
    }

    @Override
    public void displayInfo() {
        System.out.println("VisualizationFrame Info:");
        System.out.println("Width: " + getWidth());
        System.out.println("Height: " + height);
        System.out.println("Background Color: " + backgroundColor);
        System.out.println("Interactive?: " + isInteractive);
        System.out.println("Frame Type: " + frameType);
        System.out.println("Resizable?: " + isResizable);
        System.out.println("Z-Index: " + zIndex);
    }
}

class VisualizationLayer extends Visualization implements Serializable {
    private String layerName;
    protected int opacity;
    public boolean isVisible;

    public VisualizationLayer(int width, int height, String backgroundColor, boolean isInteractive,
                              String layerName, int opacity, boolean isVisible) {
        super(width, height, backgroundColor, isInteractive);
        this.layerName = layerName;
        this.opacity = opacity;
        this.isVisible = isVisible;
    }

    @Override
    public void displayInfo() {
        System.out.println("VisualizationLayer Info:");
        System.out.println("Width: " + getWidth());
        System.out.println("Height: " + height);
        System.out.println("Background Color: " + backgroundColor);
        System.out.println("Interactive?: " + isInteractive);
        System.out.println("Layer Name: " + layerName);
        System.out.println("Opacity: " + opacity);
        System.out.println("Visible?: " + isVisible);
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("data.txt"))) {
            while (true) {
                System.out.println("Enter VisualizationFrame data:");
                System.out.print("Width: ");
                int width = scanner.nextInt();
                System.out.print("Height: ");
                int height = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Background Color: ");
                String backgroundColor = scanner.nextLine();
                System.out.print("Interactive visualization (true/false): ");
                boolean isInteractive = scanner.nextBoolean();
                scanner.nextLine();
                System.out.print("Frame Type: ");
                String frameType = scanner.nextLine();
                System.out.print("Resizable (true/false): ");
                boolean isResizable = scanner.nextBoolean();
                System.out.print("Z-Index: ");
                int zIndex = scanner.nextInt();

                VisualizationFrame frame = new VisualizationFrame(width, height, backgroundColor, isInteractive,
                        frameType, isResizable, zIndex);

                objectOutputStream.writeObject(frame);

                System.out.println("Do you want to continue entering VisualizationFrame data? (yes/no)");
                String choice = scanner.next();

                if (choice.equalsIgnoreCase("no")) {
                    break;
                } else if (!choice.equalsIgnoreCase("yes")) {
                    System.out.println("Invalid input. Continuing data entry by default.");
                }
                scanner.nextLine();
            }

            while (true) {
                System.out.println("Enter VisualizationLayer data:");
                System.out.print("Width: ");
                int width = scanner.nextInt();
                System.out.print("Height: ");
                int height = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Background Color: ");
                String backgroundColor = scanner.nextLine();
                System.out.print("Interactive visualization (true/false): ");
                boolean isInteractive = scanner.nextBoolean();
                scanner.nextLine();
                System.out.print("Layer Name: ");
                String layerName = scanner.nextLine();
                System.out.print("Opacity: ");
                int opacity = scanner.nextInt();
                System.out.print("Visible (true/false): ");
                boolean isVisible = scanner.nextBoolean();

                VisualizationLayer layer = new VisualizationLayer(width, height, backgroundColor, isInteractive,
                        layerName, opacity, isVisible);

                objectOutputStream.writeObject(layer);

                System.out.println("Do you want to continue entering VisualizationLayer data? (yes/no)");
                String choice = scanner.next();

                if (choice.equalsIgnoreCase("no")) {
                    break;
                } else if (!choice.equalsIgnoreCase("yes")) {
                    System.out.println("Invalid input. Continuing data entry by default.");
                }
                scanner.nextLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("data.txt"))) {
            while (true) {
                try {
                    Object obj = objectInputStream.readObject();

                    if (obj instanceof VisualizationFrame) {
                        VisualizationFrame frame = (VisualizationFrame) obj;
                        System.out.println("\nVisualizationFrame Info:");
                        frame.displayInfo();
                    } else if (obj instanceof VisualizationLayer) {
                        VisualizationLayer layer = (VisualizationLayer) obj;
                        System.out.println("\nVisualizationLayer Info:");
                        layer.displayInfo();
                    }
                } catch (EOFException e) {
                    break; // Выход из цикла, если достигнут конец файла
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        scanner.close();
    }
}
