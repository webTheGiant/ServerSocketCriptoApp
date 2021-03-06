/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stefano;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.Random;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 *
 * @author lukie
 */
public class RSA {
    
    //scegli due primi p e q
    private BigInteger p,q,n;
     
     private BigInteger phiN;
     private BigInteger e;
     private BigInteger d;
     
     public RSA(){
       this.p=new BigInteger(512,40,new Random());
       this.q=new BigInteger(512,40,new Random());;
       this.n=this.p.multiply(this.q);
       
       calcThetaN();
       this.e=chooseE();
       this.d=invE();
         
     }
     public BigInteger[] getPuKey()
     {
         BigInteger[] v=new BigInteger[2];
         v[0]=this.e;
         v[1]=this.n;
         return v;
     }
     public BigInteger[] getPrKey(){
         BigInteger[] v=new BigInteger[2];
         v[0]=this.d;
         v[1]=this.n;
         return v;
     }
    
    public void calcThetaN(){
        BigInteger a=this.p.subtract(BigInteger.valueOf(1));
        BigInteger b=this.q.subtract(BigInteger.valueOf(1));
        this.phiN=a.multiply(b);
               
    }
    public BigInteger chooseE(){
        System.out.println("Inizio ricerca");

       do {
            e = new BigInteger(1024, new Random());
        } while ((e.compareTo(phiN) != 1)
                || (e.gcd(phiN).compareTo(BigInteger.valueOf(1)) != 0));        
                
            
      return e;
        
                        
    }
    public BigInteger invE(){
        //ottieni inverso di d=e^-1; in mod(thetaN)
        this.d=this.e.modInverse(this.phiN);
        System.out.println("Coppia di choavi completata");
        return d;
    }
    public String encryptPu(String s) throws UnsupportedEncodingException
    {
         //si cripta trovando c:=plaintext^e mod n;
        BigInteger c=new BigInteger(s.getBytes());
        c=c.modPow(this.e, this.n);
        String encr = new BASE64Encoder().encode(c.toByteArray());
        return encr;
    }
     public String decryptPr(String s) throws UnsupportedEncodingException, IOException
     {
         
         byte[] decod = new BASE64Decoder().decodeBuffer(s);
         BigInteger crypto=new BigInteger(decod);
         crypto=crypto.modPow(this.d,this.n);
         String plaintext=new String(crypto.toByteArray());
         return plaintext;
     }
     public String encryptPr(String s) throws UnsupportedEncodingException
    {
         //si cripta trovando c:=plaintext^e mod n;
        BigInteger c=new BigInteger(s.getBytes());
        c=c.modPow(this.d, this.n);
        String encr = new BASE64Encoder().encode(c.toByteArray());
        return encr;
    }
     public String decryptPu(String s) throws UnsupportedEncodingException, IOException
     {
         
         byte[] decod = new BASE64Decoder().decodeBuffer(s);
         BigInteger crypto=new BigInteger(decod);
         crypto=crypto.modPow(this.e,this.n);
         String plaintext=new String(crypto.toByteArray());
         return plaintext;
     }
     public byte[] encryptPuByte(byte[] s) throws UnsupportedEncodingException
    {
         //si cripta trovando c:=plaintext^e mod n;
        BigInteger c=new BigInteger(s);
        c=c.modPow(this.e, this.n);
        return c.toByteArray();
    }
     public byte[] decryptPrByte(byte[] decod) throws UnsupportedEncodingException, IOException
     {
         
         
         BigInteger crypto=new BigInteger(decod);
         crypto=crypto.modPow(this.d,this.n);
         return crypto.toByteArray();
     }
      public byte[] encryptPrByte(byte[] s) throws UnsupportedEncodingException
    {
         //si cripta trovando c:=plaintext^e mod n;
        BigInteger c=new BigInteger(s);
        c=c.modPow(this.d, this.n);
        return c.toByteArray();
    }
     public byte[] decryptPuByte(byte[] decod) throws UnsupportedEncodingException, IOException
     {
         
         
         BigInteger crypto=new BigInteger(decod);
         crypto=crypto.modPow(this.e,this.n);
         return crypto.toByteArray();
     }
   
    
}
