package calculator;

enum Command {
  HELP("/help"),
  EXIT("/exit");
  
  public final String command;
  
  Command(String command) {
    this.command = command;
  }
  
  String getCommand() {
    return this.command;
  }
}
