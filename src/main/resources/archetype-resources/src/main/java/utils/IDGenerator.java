#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package utils;

public interface IDGenerator {
	public long getID();
	
	public String getIDStr();
}
