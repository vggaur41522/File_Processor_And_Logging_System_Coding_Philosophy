package com.process.factory;

import com.model.ContextFromFileName;
import com.process.IProcess;
import com.process.impl.ProcessCsv;
import com.process.impl.ProcessExcel;

public class ProcessFactory {

	// Factory pattern to arrive at correct implementation of Processor
	public IProcess getProcessInstance(ContextFromFileName ctx){
		if(ctx.getPattern() == "EXCEL")
			return new ProcessExcel();
		else
			return new ProcessCsv();
	}
}
