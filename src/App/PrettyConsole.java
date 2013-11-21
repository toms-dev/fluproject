package App;
public class PrettyConsole {
	private static final int width = 55;
	//private static final int padding = 1;
	public static String HeaderTextBox(String header, String message){
		StringBuilder sb = new StringBuilder();
		String fitMessage = fit(message, width),
				fitHeader = fit(header, width);
		
		sb.append("╭─");
		
		for(int x = 0 ; x < width ; x++){
			sb.append("─");
		}
		
		sb.append("─╮\n").append("│ ").append(fitHeader.substring(0, fitHeader.length()-1)).append(" │\n");
		sb.append("├─");
		for (int x = 0; x < width ; x++ ) sb.append('─');
		sb.append("─┤\n");
		
		String[] lines = fitMessage.split("\n");
		int linesNum = lines.length;
		for(int i = 0 ; i < linesNum ; i ++){
			sb.append("│ ").append(lines[i]).append(" │\n");
		}
		sb.append("╰─");
		for(int x = 0 ; x < width ; x++){
			sb.append("─");
		}
		sb.append("─╯");
	
		return sb.toString() ;
	}
	
	public static String fit(String s, int width){
		StringBuilder sb = new StringBuilder();
		String[] lines = s.split("\n");
		for(String l : lines){
			int i = 1;
			for(char c : l.toCharArray()){
				sb.append(c);
				if (i % width == 0) {
					sb.append("\n");
					i = 0;
				}
				i++;
			}
			
			while(i <= width){
				sb.append(' ');
				i++;
			}
			sb.append("\n");
		}
		return sb.toString();
	}
}
