package main

import (
	"io"
	"os"
)

func main() {
	// create required symlink
	os.Symlink("/splunk-otel-javaagent.jar", "/javaagent.jar")
	
	// run cp with slim resources and no error checking
	source, _ := os.Open(os.Args[1:][0])
	defer source.Close()
	destination, _ := os.Create(os.Args[1:][1])
	defer destination.Close()
	io.Copy(destination, source)
}