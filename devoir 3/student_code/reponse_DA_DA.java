package student_code;

import java.util.*;

//#############################################################
//##################  QUESTIONS-STRING  #######################
//#############################################################

public class reponse_DA_DA {

    /**
     * Méthode qu'on va utiliser pour retourner le chemin de la question (pas optimal, i know,
     * mais flemme d'utiliser des structures que jcomprend pas la verite
     *
     * @param input
     * @return
     */
    public String question_1(String input) {
        structure_question_1 test = new structure_question_1(); //on instancie la question
        int[] chemin = test.question_1(input); //le chemin est juste le array retourne de la question_1
        String cheminfin = ""; //initialement vide
        int i = 0;
        while (i < chemin.length) {
            if (i > 0) {
                cheminfin += " ";
            }
            cheminfin += chemin[i];
            i++;
        }
        return cheminfin;
    }

    /**
     * Methode pr instancier lobjet test de classe structure_q2, et donner la reponse
     * @param input
     * @return
     */
    public String question_2(String input) {
            structure_question_2 test = new structure_question_2(); //
            return test.structure_question_2(input);
    }

    /**
     * Methode pr instancier lobjet test de classe structure_q2, et donner la reponse
     * @param input
     * @return
     */
    public String question_3(String input) {
        structure_question_3 test = new structure_question_3();
        return test.structure_question_3(input);
    }
}

//#############################################################
//##################    STRUCTURE Q1    #######################
//#############################################################

class structure_question_1 {

    /**
     * fct dans structure
     * @param input
     * @return
     */
    public int[] question_1(String input) {
        Arbre egouts = new Arbre();
        egouts.egoutconstru(input);

        if (egouts.structure.size() == 0) {
            return new int[0]; //l array a return est vide
        }

        String[] lignes = input.split("\n");  //on separe les instru par retours de ligne \n
        int nodeContamine = Integer.parseInt(lignes[0]); //IMPORTANT: CEST LA NODE DE DEPART DE CONTAMINATION


        return egouts.contamination(nodeContamine);
    }
}

     class Node {
        int numero; //le num du point
        Node parent; //POUR REMONTER
        List<Node> enfants; //pr chaque noeud on liste ses conexions

        public Node(int numero) { //constructeur
            this.numero = numero; //chaque node a un num
            this.enfants = new ArrayList<>();//on déclare quil a au moin une connexion
            this.parent=null; //faut prevoir si node est racine donc 0 parents
        }

        public void addConnexion(Node e) {
            this.enfants.add(e);//on ajoute les conexions independamment  de la constru de l arbre

        }
    }
     class Arbre{
        public List<Node> structure;
        public int nbrNode;
        public Arbre() {

            this.structure = new ArrayList<>();
            nbrNode=0;
        }

        public void egoutconstru(String in) {

            String[] intraite = in.split("\n");

            int i = 1; //(neud contamine ==== premier pas a consirerer dans la constru)
            while (i < intraite.length) {
                String line = intraite[i].trim();
                if (line.equals("-1")) {
                    break;
                }

                String[] parties = line.split(" ");
                int parent = Integer.parseInt(parties[0]); // indx 0 = parent

                int j = 1;
                while (j < parties.length) {
                    int enfant = Integer.parseInt(parties[j]); // indx 1++ = enfants
                    addLien(parent, enfant);
                    j++;
                }
                i++;
            }

        }

         /**
          * Methode pour get la node
          *
          * @param numero
          * @return
          */
        public Node getNode(int numero) {
            int i=0;
            while (i < structure.size()){
                if (structure.get(i).numero == numero){
                    return structure.get(i);
                }i++;
            }
            return null; //si on cherche un inexsistant
        }
        public Node getParent(int e){
            Node node = getNode(e);
            if (node == null) { // Vérification pour éviter une erreur si le nœud n'existe pas
                return null;
            }
            return node.parent;
        }

        public void addLien(int parent, int enfant) {
            Node noeudParent = getNode(parent);
            Node noeudEnfant = getNode(enfant);

           if (noeudParent == null){ //CAS SI PARENT EXISTAIT PAS
               noeudParent = new Node(parent);
               structure.add(noeudParent);
           }
            if (noeudEnfant == null){
                noeudEnfant = new Node(enfant); //CAS SI ENFANT EXISTAIT PAS
                structure.add(noeudEnfant);
            }
            if (noeudParent.enfants.contains(noeudEnfant) == false){ //ON A CREE LES 2 ANYWAYS, VERIF LIEN PUIS ADD
            noeudParent.enfants.add(noeudEnfant);
            noeudEnfant.parent = noeudParent;

            }
        }

         /**
          * Methode qui va propager la contamination
          * @param s
          * @return
          */
        public int[] contamination(int s){
            Node nodeConta = getNode(s);
            if (nodeConta == null) {
                return new int[0]; //PROBLEME VIDE
            }
            Node iterater = nodeConta;

            int taille = 0;

            while (iterater != null) {
                taille++;
                iterater = iterater.parent;
            } //ici ON SAIT YA COMBIEN D ANCETRES

            int[] cheminContamination = new int[taille];

            iterater = nodeConta;
            int i=0;
            while (iterater != null) {
                cheminContamination[i] = iterater.numero;
                iterater = iterater.parent;
                i++;
            }
            return cheminContamination;
        }
    }

//##################    STRUCTURE Q2    #######################
//#############################################################

class structure_question_2 {
    Maxheap heap;
    public structure_question_2() {
        heap = new Maxheap(100); //juuuuust in case
    }

    public String structure_question_2(String input) {
        String[] lignes = input.split("\n");

        for (int i = 0; i < lignes.length; i++) {
            String ligne = lignes[i].trim();
            if (ligne.isEmpty()) continue;

            String[] parties = ligne.split("\\$");
            if (parties.length != 2) continue;

            String valeurStr = parties[0].trim();
            String item = parties[1].trim();

            boolean estEntier = true;
            for (int j = 0; j < valeurStr.length(); j++) {
                char c = valeurStr.charAt(j);
                if (!Character.isDigit(c)) { //meme chose que Integer.parseInt, mais pour Char
                    estEntier = false;
                    break;
                }
            }

            if (estEntier) {
                int valeur = Integer.parseInt(valeurStr);
                heap.inserer(valeur, item);
            }
        }

        StringBuilder sortie = new StringBuilder();
        while (heap.size > 0) {
            HeapNode max = heap.supprime_top();
            sortie.append(max.item).append("\n");
        }

        return sortie.toString().trim();
    }

}

    class HeapNode {
        public int value;
        public String item;

        public HeapNode(int value, String item) {
            this.value = value;
            this.item = item;
        }
    }
    class Maxheap {
        public HeapNode[] heap;
        public int size;
        public int capacity;

        public Maxheap(int capacity) {
            this.capacity = capacity;
            this.size = 0;
            this.heap = new HeapNode[capacity + 1]; // Indexing starts from 1 comme au cours de gilles brassard
        }

        public int getParent(int index) {
            return index / 2;
        }

        public int getleft(int index) {
            return index * 2;
        }

        public int getright(int index) {
            return index * 2 + 1;
        }

        public void swap(int i, int j) {
            HeapNode temp = heap[i];
            heap[i] = heap[j];
            heap[j] = temp;
        }

        // Insert un element dans le heap
        public void inserer(int value, String item) {
            if (size >= capacity) {
                throw new IllegalStateException("Heap is full");
            }

            size++;
            heap[size] = new HeapNode(value, item);
            int k = size;

            while (k > 1 && heap[getParent(k)].value < heap[k].value) {
                swap(k, getParent(k));
                k = getParent(k);
            }
        }

        // Get the maximum element (root) without removing it
        public HeapNode top() {
            if (size == 0) {
                throw new IllegalStateException("Heap is empty");
            }
            return heap[1];
        }

        // Remove and return the max element (root)
        public HeapNode supprime_top() {
            if (size == 0) {
                throw new IllegalStateException("Heap is empty");
            }

            HeapNode max = heap[1]; // Store max element
            heap[1] = heap[size]; // deplace le dernier element dans la racine
            size--; // Reduce size
            heapifyDown(1); // Restorer les propriete du heap

            return max;
        }

        // Fix le heap apres suppression (heapify down)
        public void heapifyDown(int index) {
            int left = getleft(index);
            int right = getright(index);
            int largest = index;

            if (left <= size && heap[left].value > heap[largest].value) {
                largest = left;
            }
            if (right <= size && heap[right].value > heap[largest].value) {
                largest = right;
            }
            if (largest != index) {
                swap(index, largest);
                heapifyDown(largest); // Recursively fix the heap
            }
        }

        // Print elements dans l'ordre original sans modifier le heap
        public void itemToSteal() {
            if (size == 0) {
                System.out.println("Heap is empty");
                return;
            }

            Maxheap tempHeap = new Maxheap(capacity);
            for (int i = 1; i <= size; i++) {
                tempHeap.inserer(heap[i].value, heap[i].item);
            }

            while (tempHeap.size > 0) {
                HeapNode removed = tempHeap.supprime_top();
                System.out.println("Value: " + removed.value + ", Item: " + removed.item);
            }
        }
    }

//##################    STRUCTURE Q3    #######################
//#############################################################
class structure_question_3 {

    public String structure_question_3(String input) {
        ArbreAVL arbre = new ArbreAVL();
        StringBuilder sortie = new StringBuilder();

        try {
            String[] lignes = input.split("\n");

            for (int i = 0; i < lignes.length; i++) {
                String ligne = lignes[i].trim();
                if (ligne.isEmpty()) {
                    continue;
                }

                String[] parts = ligne.split("\\s+");

                if (parts[0].equals("A") && parts.length == 3) {
                    int cle = Integer.parseInt(parts[1]);
                    String couleur = parts[2];
                    arbre.racine = arbre.ajouter(arbre.racine, cle, couleur);
                }

                else if (parts[0].equals("S") && parts.length == 2) {
                    int cle = Integer.parseInt(parts[1]);

                    NodeAVL cible = arbre.keyNode(cle);
                    String couleur;
                    if (cible == null) {
                        couleur = "#inconnue";
                    } else {
                        couleur = cible.couleur;
                    }

                    arbre.supprimer(cle);

                    int hauteur = arbre.getHauteurArbre(); // méthode propre que tu as ajoutée

                    sortie.append(couleur);
                    sortie.append(" ");
                    sortie.append(hauteur);
                    sortie.append("\n");
                }
            }

        } catch (Exception e) {
            return "Exception : " + e.getMessage();
        }

        return sortie.toString().trim();
    }


}

    class NodeAVL {
        int key;
        int hauteur;
        String couleur;
        NodeAVL left;
        NodeAVL right;

        /**
         * Constructeur
         * @param key
         * @param couleur
         */
        NodeAVL(int key, String couleur) {
            this.key = key;
            this.couleur = couleur;
            this.hauteur = 0;
            this.left = null;
            this.right = null;
        }

    }
    class ArbreAVL{

        NodeAVL racine;

        /**
         * Methode qui retourne la hauteur (attribut connu) d'un node
         * @param node
         * @return
         */
        public int getHauteur(NodeAVL node) {
            if(node==null){ //cas mauvai
                return -1;
            }
            return node.hauteur;
        }

        public int getHauteurArbre() {
            ArbreHauteur(racine); // 🔁 recalculer toutes les hauteurs
            if (racine == null) {
                return 0;
            }
            return racine.hauteur + 1;
        }

        /**
         * Methode pour mettre nouvelle hauteur (cas apres rotations)
         * Si jamais on a un node qui a 2 enfant et un des enfants est
         * plus profond alors on donne au parent la hauteur de l'enfant le plus profond +1
         * @param node
         */
        public void setHauteur(NodeAVL node) {

            int max_hauteur; //LA HAUTEUR DE L'ENFANT POTENTIELLEMENT LE PLUS PROFOND
            // -----CHERCHER LES 2 CAS SOIT GAUCHE+ PROFOND SOIT DROITE + PROFOND -----//
            if (getHauteur(node.left)>getHauteur(node.right)){
                max_hauteur = getHauteur(node.left)+1;
            }
            else{
                max_hauteur = getHauteur(node.right)+1;
            }
            node.hauteur = max_hauteur;


        }

        /**
         * Methode qui calcul la difference de hauteur des 2 potentiels enfants/s.a d'un e node
         * @param node
         * @return
         */
        public int getBalance(NodeAVL node) {
            int diff_balance;
            diff_balance = getHauteur(node.left) - getHauteur(node.right);
            return diff_balance;
        }

        /**
         * methode qui va trier les cas de rotation avec le get balance deja fait au debut
         * et appeller les simpledroite/simplegauche au bon cas
         * @param node
         * @return
         */
        public NodeAVL verifBalance(NodeAVL node) {
            int diff_balance = getBalance(node); // <----- OBTENU AVEC GET BALANCE ON LE CONNAIT

            if (diff_balance > 1){ //CAS ++LOURD GAUCHE
                if (getBalance(node.left) < 0) {
                    node.left = SG(node.left); // simlpe gauche avant (cas double)
                }
                node = SD(node); // simple droite si allignes tous a gauche
            }

            if (diff_balance < -1){ // CAS ++LOURD DROITE
                if (getBalance(node.right) > 0) {
                    node.right = SD(node.right);
                }
                node = SG(node);
            }

            return node;
        }


        /**
         * Verifier chaque node a partir de la racine en O(log(n)) comme un arbre binaire <br>
         * Partir gauche si key< present<br>
         * Partir droite si key >present
         * @param node
         * @return present actuel
         */
        public NodeAVL getNode(NodeAVL node) {
            NodeAVL present = this.racine; //demarre a la racine

            while (present != null) {
                if (present == node) {
                    break; //node trouve
                }
                else if (present.key < node.key) {
                    present = present.left; //partir gauche
                }
                else if (present.key > node.key) {
                    present = present.right; //partir droite
                }
                else if(present.left ==null && present.right == null) { //on est arrive a une feuille
                    present = null;
                }
            }
            return present;
        }

        public NodeAVL keyNode(int key) {
            NodeAVL courant = racine;

            while (courant != null) {
                if (key == courant.key) {
                    return courant;
                } else if (key < courant.key) {
                    courant = courant.left;
                } else {
                    courant = courant.right;
                }
            }

            return null;
        }


        /**
         * METHODE POUR ECHANGER WOW
         * @param y le node probleme
         * @return
         */
        public NodeAVL SD(NodeAVL y){

            NodeAVL x = y.left; // enfant gauche de y
            NodeAVL T2 = x.right; //le S.A DROITE DE X

            x.right = y; //x devient PARENT DE Y
            y.left = T2; // ON MET LES ENFANTS SUP DE Y A GAUCHE DE Y (CAR INFERIEURS A LA BASE A Y )

            setHauteur(y); //ON CALCULE HAUTEUR  ++PROFOND DES ENFATNS Y, ON FAIT +1
            setHauteur(x);

            return x;
        }
        public NodeAVL SG(NodeAVL x){
            NodeAVL y = x.right;
            NodeAVL T2 = y.left;

            y.left = x;
            x.right = T2;

            setHauteur(x);
            setHauteur(y);

            return y;
        }

        public NodeAVL ajouter(NodeAVL node, int key, String couleur) {
            if (node == null) {
                return new NodeAVL(key, couleur);
            }



            if (key < node.key) {
                node.left = ajouter(node.left, key, couleur);
            } else if (key > node.key) {
                node.right = ajouter(node.right, key, couleur);
            } else {
                return node; //
            }


            node = verifBalance(node);
            setHauteur(node);

            return node;
        }

        public void supprimer(int key) {
            racine = supprimer(racine, key); //APPEL SUR ARBRE, ANYWAYS ON VERIF TT LES PROBLEMS
        }

        private NodeAVL supprimer(NodeAVL node, int key) {
            if (node == null) {
                return null; //PAS LA
            }


            if (key < node.key) {
                node.left = supprimer(node.left, key); //MM CHOSE QUE CHERCHE EN O(LOG(N))
            } else if (key > node.key) {
                node.right = supprimer(node.right, key);
            } else {

                if (node.left == null && node.right == null) {
                    return null; // pas d'enfants
                }
                else if (node.left == null) {
                    return node.right; // un seul enfant (droite)
                }
                else if (node.right == null) {
                    return node.left; // un seul enfant (gauche)
                } else {

                    NodeAVL predecesseur = trouverMax(node.left);
                    node.key = predecesseur.key;
                    node.couleur = predecesseur.couleur;
                    node.left = supprimer(node.left, predecesseur.key);
                }
            }

            //-------------CORRECTION CAR DEBALANCED PEUT ETRE ---//
            setHauteur(node);
            return verifBalance(node);
        }

        public NodeAVL trouverMax(NodeAVL node) { //oui...jai lu le forum...omg je prenais le min immediat tbrnk
            while (node.right != null) {
                node = node.right;
            }
            return node;
        }

        public void ArbreHauteur(NodeAVL node) {
            if (node == null) return;
            ArbreHauteur(node.left);
            ArbreHauteur(node.right);
            setHauteur(node);
        }


        public void affichageArbre() {
            affichage(racine);
        }

        public void affichage(NodeAVL node) {
            if (node != null) {

                String gauche ="";
                String droite="";
                // --------------------//
                if (node.left != null){
                    gauche =  String.valueOf(node.left.key);
                }else{
                    gauche =null;
                }

                if (node.right != null){
                    droite =  String.valueOf(node.right.key);
                }else{
                    droite =null;
                }
                System.out.println("Node " + node.key + " : enfant gauche = " + gauche + ", enfant droite = " + droite);

                //RECURSE -> GAUCHE DOITE
                affichage(node.left);
                affichage(node.right);
            }
        }

    }