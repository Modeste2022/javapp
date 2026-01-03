package util;

import java.io.*;

public class Serializer {

    // Méthode pour sérialiser un objet dans un fichier
    public static void sauvegarder(Object objet, String cheminFichier) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(cheminFichier))) {
            oos.writeObject(objet);
        }
    }

    // Méthode pour désérialiser un objet depuis un fichier
    @SuppressWarnings("unchecked")
    public static <T> T charger(String cheminFichier) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(cheminFichier))) {
            return (T) ois.readObject();
        }
    }
}
