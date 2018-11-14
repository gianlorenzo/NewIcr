package it.uniroma3.icr.service.editor;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.uniroma3.icr.model.Image;
import it.uniroma3.icr.service.impl.ImageService;

@Component
public class ImageEditor extends PropertyEditorSupport {
	private @Autowired
    ImageService imageService;
	
	@Override
	public void setAsText(String text) {
		Image i = this.imageService.retrieveImage(Long.valueOf(text));
		this.setValue(i);
	}
	
}
