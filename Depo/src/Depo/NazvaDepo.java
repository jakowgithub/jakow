package Depo;
import java.io.Serializable;

public enum NazvaDepo implements Serializable {DepoDarniza(300),DepoGeroivDnipra(250),DepoSirez(200);
int zagalnaEmnistVagoniv;
NazvaDepo (int zEV){zagalnaEmnistVagoniv=zEV;}
int getZagalnaEmnistVagoniv(){return zagalnaEmnistVagoniv;}
}
