package controller.command;

import javax.xml.ws.RequestWrapper;

/**
 * Created by tonchief on 05/27/2017.
 */
public interface Command {
    String execute(RequestWrapper req);
}
