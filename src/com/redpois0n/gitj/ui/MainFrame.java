package com.redpois0n.gitj.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.redpois0n.git.Repository;
import com.redpois0n.gitj.Main;
import com.redpois0n.gitj.Version;
import com.redpois0n.gitj.utils.IconUtils;

import javax.swing.JButton;
import javax.swing.ImageIcon;

@SuppressWarnings("serial")
public class MainFrame extends JFrame {

	private JPanel contentPane;
	private JTabbedPane tabbedPane;

	public MainFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 700);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenu mnRepository = new JMenu("Repository");
		menuBar.add(mnRepository);
		
		JMenuItem mntmRefresh = new JMenuItem("Refresh");
		mntmRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				reloadCurrentRepo();
			}
		});
		mnRepository.add(mntmRefresh);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		contentPane.add(toolBar, BorderLayout.NORTH);
		
		JButton btnCloneNew = new JButton("Clone/New");
		btnCloneNew.setIcon(IconUtils.getIcon("database-add"));
		toolBar.add(btnCloneNew);
		
		toolBar.addSeparator();
		
		JButton btnCommit = new JButton("Commit");
		btnCommit.setIcon(IconUtils.getIcon("commit"));
		toolBar.add(btnCommit);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.setIcon(IconUtils.getIcon("add-big"));
		toolBar.add(btnAdd);
		
		JButton btnRemove = new JButton("Remove");
		btnRemove.setIcon(IconUtils.getIcon("remove-big"));
		toolBar.add(btnRemove);
		
		JButton btnFetch = new JButton("Fetch");
		btnFetch.setIcon(IconUtils.getIcon("fetch"));
		toolBar.add(btnFetch);
		
		JButton btnPull = new JButton("Pull");
		btnPull.setIcon(IconUtils.getIcon("pull"));
		toolBar.add(btnPull);
		
		JButton btnPush = new JButton("Push");
		btnPush.setIcon(IconUtils.getIcon("push"));
		toolBar.add(btnPush);
		
		JSplitPane splitPane = new JSplitPane();
		contentPane.add(splitPane, BorderLayout.CENTER);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.addChangeListener(new TabChangeListener());
		splitPane.setRightComponent(tabbedPane);
	}

	/**
	 * Loads repository in new panel
	 * @param repository
	 */
	public void loadRepository(Repository repository) {
		try {			
			MainPanel pane = new MainPanel(this, repository);
			
			addPanel(repository.getFolder().getName(), pane);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * Add tab
	 * @param title Tab title
	 * @param panel
	 */
	public void addPanel(String title, AbstractPanel panel) {
		tabbedPane.addTab(title, panel);
	}
	
	/**
	 * Gets selected repo tab
	 * @return
	 */
	public Repository getSelectedRepo() {
		Component c = tabbedPane.getSelectedComponent();
		
		if (c instanceof AbstractPanel) {
			AbstractPanel mp = (AbstractPanel) c;
			
			return mp.getRepository();
		}
		
		return null;
	}
	
	/**
	 * Gets selected tab
	 * @return
	 */
	public AbstractPanel getSelectedPanel() {
		Component c = tabbedPane.getSelectedComponent();
		
		if (c instanceof AbstractPanel) {
			return (AbstractPanel) c;
		}
		
		return null;
	}
	
	/**
	 * Reloads selected repo
	 */
	public void reloadCurrentRepo() {
		AbstractPanel mp = getSelectedPanel();
		
		if (mp != null) {
			try {
				mp.reload();
			} catch (Exception e) {
				e.printStackTrace();
				Main.displayError(e);
			}
		}
	}
	
	/**
	 * Sets title beginning with "gitj - "
	 */
	@Override
	public void setTitle(String title) {
		super.setTitle("gitj " + Version.getVersion() + " - " + title);
	}
	
	public class TabChangeListener implements ChangeListener {
		
		/**
		 * Called when tab changed
		 */
		@Override
		public void stateChanged(ChangeEvent arg0) {
			Repository repo = MainFrame.this.getSelectedRepo();
			
			if (repo != null) {
				MainFrame.this.setTitle(repo.getName());
			}
		}
	}
	
}
