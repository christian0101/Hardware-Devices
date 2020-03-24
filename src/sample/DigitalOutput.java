import com.phidgets.*;
import com.phidgets.event.*;
import java.util.*;

public class DigitalOutput {
  public static void sound(InterfaceKitPhidget ik, int out) {
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

  public static final void main(String args[]) throws Exception {
    InterfaceKitPhidget ik;

    // Example of enabling logging.
    // Phidget.enableLogging(Phidget.PHIDGET_LOG_VERBOSE, null);

    System.out.println(Phidget.getLibraryVersion());

    ik = new InterfaceKitPhidget();
    ik.addAttachListener(new AttachListener() {
      public void attached(AttachEvent ae) {
        System.out.println("attachment of " + ae);
      }
    });
    ik.addDetachListener(new DetachListener() {
      public void detached(DetachEvent ae) {
        System.out.println("detachment of " + ae);
      }
    });
    ik.addErrorListener(new ErrorListener() {
      public void error(ErrorEvent ee) {
        System.out.println(ee);
      }
    });
    ik.addInputChangeListener(new InputChangeListener() {
      public void inputChanged(InputChangeEvent oe) {
        System.out.println(oe);
      }
    });
    ik.addOutputChangeListener(new OutputChangeListener() {
      public void outputChanged(OutputChangeEvent oe) {
        System.out.println(oe);
      }
    });
    ik.addSensorChangeListener(new SensorChangeListener() {
      public void sensorChanged(SensorChangeEvent se) {
      }
    });

    ik.openAny();
    System.out.println("waiting for InterfaceKit attachment...");
    ik.waitForAttachment();

    System.out.println(ik.getDeviceName());

    Thread.sleep(500);

    sound(ik, 0);
  }
}
