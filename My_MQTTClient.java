import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import java.io.DataInputStream;
import java.io.FileInputStream;


public class My_MQTTClient {
  public static void main(String[] args) {
    String topic        = "assignment2";   
    String content      ="";			 // = "Hello Users from CloudMQTT Broker";
    int qos             = 1;
    String broker       = "tcp://m20.cloudmqtt.com:19589";
	

    //MQTT client id to use for the device. "" will generate a client id automatically
    String clientId     = "ClientId"; try{

                 DataInputStream myDataStream =
                 new DataInputStream (
                 new FileInputStream ("/home/superstaruser/Assignment2/output.txt"));

                 byte[] myDataInBytes = new byte[myDataStream.available()];
                 myDataStream.readFully(myDataInBytes);
                 myDataStream.close();

                 String myFileContent = new String(myDataInBytes, 0, myDataInBytes.length);

		 content = myFileContent;
                 //System.out.println(myFileContent);

        }catch(Exception ex){
                ex.printStackTrace();
        }

    MemoryPersistence persistence = new MemoryPersistence();
    try {
      MqttClient mqttClient = new MqttClient(broker, clientId, persistence);
      mqttClient.setCallback(new MqttCallback() {
        public void messageArrived(String topic, MqttMessage msg)
                  throws Exception {
                      System.out.println("Recived:" + topic);
                      System.out.println("Recived:" + new String(msg.getPayload()));
                }

        public void deliveryComplete(IMqttDeliveryToken arg0) {
                    System.out.println("Delivary of these messages complete: fro Shell Script and javaFile.");
                }

        public void connectionLost(Throwable arg0) {
                    // TODO Auto-generated method stub
                }
      });

      MqttConnectOptions connOpts = new MqttConnectOptions();
      connOpts.setCleanSession(true);
      connOpts.setUserName("kdfqpatn");
      connOpts.setPassword(new char[]{'l', 'W', 'O', '_', 'W', 'x', 's', 'a', 'z', 'R', 'c', 'X'});
      mqttClient.connect(connOpts);
      MqttMessage message = new MqttMessage(content.getBytes());
      message.setQos(qos); 
      System.out.println("Publish message: " + message);
      mqttClient.subscribe(topic, qos);
      mqttClient.publish(topic, message);
      mqttClient.disconnect();
      System.exit(0);
    } catch(MqttException me) {
      System.out.println("reason "+me.getReasonCode());
      System.out.println("msg "+me.getMessage());
      System.out.println("loc "+me.getLocalizedMessage());
      System.out.println("cause "+me.getCause());
      System.out.println("excep "+me);
      me.printStackTrace();
    }
  }
}




