/*
 * Copyright 2007 Phidgets Inc.  All rights reserved.
 */

import com.phidgets.*;
import com.phidgets.event.*;

public class ThroughIFKit
{
	public static final void main(String args[]) throws Exception {
    InterfaceKitPhidget ik;
    TextLCDPhidget lcd;

		//Example of enabling logging.
		//Phidget.enableLogging(Phidget.PHIDGET_LOG_VERBOSE, null);

		System.out.println(Phidget.getLibraryVersion());

    lcd = new TextLCDPhidget();
    lcd.addAttachListener(new AttachListener() {

        public void attached(AttachEvent ae) {
            System.out.println("attachment of " + ae);
        }
    });

    lcd.addDetachListener(new DetachListener() {

        public void detached(DetachEvent ae) {
            System.out.println("detachment of " + ae);
        }
    });

    lcd.addErrorListener(new ErrorListener() {

        public void error(ErrorEvent ee) {
            System.out.println("error event for " + ee);
        }
    });

    lcd.openAny();
    System.out.println("Waiting for the TextLCD to be attached...");
    lcd.waitForAttachment();

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
        String strValue = "0";
        String msg = "Index " + se.getIndex() + " Value";

        // Temperature sensor
        if (se.getIndex() == 0) {
          msg = "Temp (C)";
          strValue = Double.toString((se.getValue() * 0.2222) - 61.111);
        } else {
          // other sensor
          strValue = Double.toString(se.getValue());
        }

        // DEBUG 
        System.out.println("Index " + se.getIndex() + ": " + strValue);

        try {
          lcd.setDisplayString(0, msg);
          lcd.setDisplayString(1, strValue);
        } catch(Exception e) {
          System.out.println(e);
        }
			}
		});

		ik.openAny();
		System.out.println("waiting for InterfaceKit attachment...");
		ik.waitForAttachment();

		System.out.println(ik.getDeviceName());

		Thread.sleep(500);
	}
}
