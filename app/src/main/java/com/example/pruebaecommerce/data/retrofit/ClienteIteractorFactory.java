package com.example.pruebaecommerce.data.retrofit;

public class ClienteIteractorFactory {

    private static ClientInteractor clientInteractor;

    public static ClientInteractor getClientInteractor(){
        synchronized (ClientInteractor.class){
            if (clientInteractor == null){
                clientInteractor = new ClientInteractor();
            }

            return clientInteractor;
        }
    }
}
