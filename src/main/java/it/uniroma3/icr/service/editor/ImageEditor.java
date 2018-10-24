package it.uniroma3.icr.service.editor;

import it.uniroma3.icr.model.Image;
import it.uniroma3.icr.service.impl.ImageFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.beans.PropertyEditorSupport;

@Component
public class ImageEditor extends PropertyEditorSupport {
	private @Autowired ImageFacade imageFacade;
	
	@Override
	public void setAsText(String text) {
		Image i = this.imageFacade.retrieveImage(Long.valueOf(text));
		this.setValue(i);
	}
	
}
