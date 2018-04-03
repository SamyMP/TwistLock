package client.view;

import javax.swing.JTextField;

import javax.swing.text.Document;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import javax.swing.text.AttributeSet;

/**
 * Champ d'insertion de texte avec une limite de caractères
 * @author  Javane
 * @version 2018-03-28
 */
public class JTextFieldLimit extends JTextField
{
    /**
     * The maximal number of characters.
     */
    private int limit;


    /**
     * Creates a text field with a limited number of characters to input.
     * @param limit limite de caractères
     */
    public JTextFieldLimit (int limit)
    {
        super();
        this.limit = limit;
    }

    /**
     * Changes the model of the text field to limit the input of the user.
     * @return Default model.
     */
    @Override
    protected Document createDefaultModel ()
    {
        return new LimitDocument();
    }

    /**
     * Model limiting the number of characters entered in the attached field.
     * @author  Javane
     * @version 2018-03-28
     */
    private class LimitDocument extends PlainDocument
    {
        @Override
        public void insertString (int offset, String  str, AttributeSet attr)
                throws BadLocationException
        {
            // S'il n'y a aucun contenu, aucune action n'est effectuée
            if (str == null) return;

            // Sinon, vérifie si la chaine rentrée dépasse le nombre de caractères limite
            // Si c'est le cas, la chaine n'est pas insérée dans la zone de texte
            if ( (this.getLength() + str.length()) <= limit )
                super.insertString(offset, str, attr);
        }
    }
}
