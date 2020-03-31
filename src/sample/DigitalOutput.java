package sample;
import com.phidgets.*;
import com.phidgets.event.*;
import java.util.*;

public class DigitalOutput {

  private InterfaceKitPhidget ik;
  private Integer[] forceSensorData = new Integer[4];
  private Main parent;

  DigitalOutput (Main parent) {
    this.parent = parent;
  }

  /**
   *
   * @param out output port
   */
  public void makeSound(int out) {
    int count = 0;
    while (true) {
      try {
        boolean state = Math.abs(Math.round(Math.sin(Math.sin(count)) * 1000)) != 0;
        ik.setOutputState(out, state);
        count++;
      } catch(Exception e) {
        System.out.println(e);
      }
    }
  }

  public Integer[] getForceSensorData() {
    return this.forceSensorData;
  }

  public boolean initialise() throws Exception {
    System.out.println(Phidget.getLibraryVersion());

    ik = new InterfaceKitPhidget();

    ik.addAttachListener(new AttachListener() {
      public void attached(AttachEvent ae) {
        //System.out.println("attachment of " + ae);
      }
    });

    ik.addDetachListener(new DetachListener() {
      public void detached(DetachEvent ae) {
        //System.out.println("detachment of " + ae);
      }
    });

    ik.addErrorListener(new ErrorListener() {
      public void error(ErrorEvent ee) {
        //System.out.println(ee);
      }
    });

    ik.addInputChangeListener(new InputChangeListener() {
      public void inputChanged(InputChangeEvent oe) {
        //System.out.println(oe);
      }
    });

    ik.addOutputChangeListener(new OutputChangeListener() {
      public void outputChanged(OutputChangeEvent oe) {
        //System.out.println(oe);
      }
    });

    ik.addSensorChangeListener(new SensorChangeListener() {
      public void sensorChanged(SensorChangeEvent se) {
        // TODO: add force sensor logic
        //System.out.println(se.getIndex() % 4 + " " + se.getValue());
        forceSensorData[se.getIndex() % 4] = se.getValue();

      }
    });

    ik.openAny();
    System.out.println("waiting for InterfaceKit attachment...");
    ik.waitForAttachment();

    System.out.println(ik.getDeviceName());

    Thread.sleep(500);

    return true;
  }
}
