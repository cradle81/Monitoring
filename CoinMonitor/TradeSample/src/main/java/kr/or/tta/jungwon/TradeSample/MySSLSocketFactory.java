package kr.or.tta.jungwon.TradeSample;

public class MySSLSocketFactory {
	    SSLContext sslContext = SSLContext.getInstance("TLS");
	 
	    public MySSLSocketFactory(KeyStore truststore) throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException {
	        super(truststore);
	 
	        TrustManagerFactory tmf = TrustManagerFactory.getInstance("X509");
	        tmf.init(truststore);
	 
	        sslContext.init(null, tmf.getTrustManagers(), null);
	    }
	 
	    @Override
	    public Socket createSocket(Socket socket, String host, int port, boolean autoClose) throws IOException, UnknownHostException {
	        return sslContext.getSocketFactory().createSocket(socket, host, port, autoClose);
	    }
	 
	    @Override
	    public Socket createSocket() throws IOException {
	        return sslContext.getSocketFactory().createSocket();
	    }
}
