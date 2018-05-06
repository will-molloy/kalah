package kalah;
import com.qualitascorpus.testsupport.IO;
import com.qualitascorpus.testsupport.MockIO;
import kalah.controller.Controller;
import kalah.controller.DefaultController;
import kalah.model.DefaultModel;
import kalah.model.Model;
import kalah.setting.GameSetting;
import kalah.view.DefaultView;
import kalah.view.View;
public class Kalah {
	private Model model;
	private View view;
	private Controller controller;
	public static void main(String[] args) {
		new Kalah().play(new MockIO());
	}
	public void play(IO io) {
		GameSetting.loadConfig();
		model = new DefaultModel();
		view = new DefaultView(model, io);
		controller = new DefaultController(model, view);
		controller.gameStart();
	}
}
