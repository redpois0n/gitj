package gitj.tasks;

import git.Repository;

public abstract class Task {
	
	protected String text;
	protected Repository repo;
	
	public Task(String text, Repository repo) {
		this.text = text;
		this.repo = repo;
	}
	
	public abstract void execute() throws Exception;

	public String getText() {
		return text;
	}

}
