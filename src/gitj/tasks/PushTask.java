package gitj.tasks;

import git.Branch;
import git.Remote;
import git.Repository;

public class PushTask extends Task {

	private Branch branch;
	private Remote remote;
	
	public PushTask(Repository repo, Branch branch, Remote remote) {
		super("Pushing...", repo);
		this.branch = branch;
		this.remote = remote;
	}

	@Override
	public void execute() throws Exception {
		repo.push(remote, branch);
	}

}
