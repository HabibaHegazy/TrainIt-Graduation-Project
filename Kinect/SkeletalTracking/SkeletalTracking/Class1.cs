using System;
using System.Net;
using System.Net.Sockets;
using System.Text;
using System;
using System.Threading;
using System.Threading.Tasks;
using System.Collections;

namespace SocketKinect
{
    public partial class Class1
    {

        public Class1()
        { }

        public void ClassElUDP()
        {
            IPHostEntry ipHost = Dns.GetHostEntry(Dns.GetHostName());
            IPAddress ipAddr = ipHost.AddressList[1];
            //IPEndPoint localEndPoint = new IPEndPoint(ipAddr, 12345);

            UdpClient udpClient = new UdpClient(12345);
            try
            {
                udpClient.Connect("", 12345);

                // Sends a message to the host to which you have connected.
                Byte[] sendBytes = Encoding.ASCII.GetBytes("Is anybody there?");

                udpClient.Send(sendBytes, sendBytes.Length);

                // Sends a message to a different host using optional hostname and port parameters.
                //IPEndPoint object will allow us to read datagrams sent from any source.
                IPEndPoint RemoteIpEndPoint = new IPEndPoint(IPAddress.Any, 12345);

                // Blocks until a message returns on this socket from a remote host.
                Byte[] receiveBytes = udpClient.Receive(ref RemoteIpEndPoint);
                string returnData = Encoding.ASCII.GetString(receiveBytes);

                // Uses the IPEndPoint object to determine which of these two hosts responded.
                Console.WriteLine("This is the message you received " + returnData.ToString());
                Console.WriteLine("This message was sent from " + RemoteIpEndPoint.Address.ToString() 
                                                    + " on their port number " + RemoteIpEndPoint.Port.ToString());

                udpClient.Close();
            }
            catch (Exception e)
            {
                Console.WriteLine(e.ToString());
            }

        }


        public void ClassElFunction(ArrayList data)
        {
            try
            {

                // Establish the remote endpoint 
                // for the socket. This example 
                // uses port 11111 on the local 
                // computer. 
                IPHostEntry ipHost = Dns.GetHostEntry(Dns.GetHostName());
                IPAddress ipAddr = ipHost.AddressList[1];
                IPEndPoint localEndPoint = new IPEndPoint(ipAddr, 12345);

                // Creation TCP/IP Socket using 
                // Socket Class Costructor 
                Socket sender = new Socket(ipAddr.AddressFamily, SocketType.Stream, ProtocolType.Tcp);

                try
                {

                    // Connect Socket to the remote 
                    // endpoint using method Connect() 
                    sender.Connect(localEndPoint);

                    // We print EndPoint information 
                    // that we are connected 
                    Console.WriteLine("Socket connected to -> {0} ", sender.RemoteEndPoint.ToString());

                    // Creation of messagge that 
                    // we will send to Server 
                    int[] array = new int[2];

                    foreach (int i in data)
                    {
                        byte[] messageSent = BitConverter.GetBytes(i);
                        int byteSent = sender.Send(messageSent);
                        messageSent = null;
                    }

                    // Data buffer 
                    byte[] messageReceived = new byte[1024];

                    // We receive the messagge using the method Receive(). 
                    //This method returns number of bytes received, that we'll use to convert them to string 
                    int byteRecv = sender.Receive(messageReceived);
                    Console.WriteLine("Message from Server -> {0}",
                        Encoding.ASCII.GetString(messageReceived,
                                                    0, byteRecv));

                    // Close Socket using the method Close() 
                    sender.Shutdown(SocketShutdown.Both);
                    sender.Close();
                }

                // Manage of Socket's Exceptions 
                catch (ArgumentNullException ane)
                {

                    Console.WriteLine("ArgumentNullException : {0}", ane.ToString());
                }

                catch (SocketException se)
                {

                    Console.WriteLine("SocketException : {0}", se.ToString());
                }

                catch (Exception e)
                {
                    Console.WriteLine("Unexpected exception : {0}", e.ToString());
                }
            }

            catch (Exception e)
            {

                Console.WriteLine(e.ToString());
            }
            Console.WriteLine(data[0]);
        }


    }




}
