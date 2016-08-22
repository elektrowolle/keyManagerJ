import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import mraa.IntelEdison;
import tinyb.*;
import mraa.mraaJNI;

public class KeyManagerJ {
	static final int DISCOVERY_TRIALS = 150;
	static BluetoothManager manager = BluetoothManager.getBluetoothManager();
	public static void main(String[] args) {
		(new KeyManagerJ()).run(); 
	  
		
	  
	}
	
	
	private void run() {
		boolean isDiscovering = manager.startDiscovery();
		  
		ArrayList<BluetoothDevice> devices = discoverDevices();
		pair(devices);
		
	}


	private void pair(ArrayList<BluetoothDevice> devices) {
		for (BluetoothDevice device : devices) {
			System.out.printf(
					"%s %s \n",
					(device.pair()? "Paired" : "Couldn't pair"),
					device.getName()
					);
		}
		
	}


	private ArrayList<BluetoothDevice> discoverDevices() {
		ArrayList<BluetoothDevice> devicesToPair = new ArrayList<>();
		for (int i = 0; i < DISCOVERY_TRIALS; i++) {
			List<BluetoothDevice> devices = manager.getDevices();
			System.out.printf("Trial: %d\n", i);
			for (BluetoothDevice device : devices) {
				System.out.printf("found %s [%s](%d dbI): %s\n",
						device.getPaired() ? "paired" : "unpaired",
						device.getAddress(),
						device.getRssi(),
						device.getAlias()
						);	
				if(
						device.getRssi() > -12 &&
						!device.getPaired() &&
						!devicesToPair.contains(device)
						){
					System.out.print("Add to pairing List");
					devicesToPair.add(device);
				}
			}
			try {
				Thread.sleep(1000l);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return devicesToPair;
		
	}

}