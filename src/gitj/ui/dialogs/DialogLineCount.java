package gitj.ui.dialogs;

import javax.swing.JDialog;

import git.Repository;

@SuppressWarnings("serial")
public class DialogLineCount extends JDialog {

	private Repository repo;
	
	public DialogLineCount(Repository repo) {
		this.repo = repo;
		setBounds(100, 100, 450, 300);

	}
}
