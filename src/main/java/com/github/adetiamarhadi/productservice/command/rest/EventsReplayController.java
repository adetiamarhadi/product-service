package com.github.adetiamarhadi.productservice.command.rest;

import org.axonframework.config.EventProcessingConfiguration;
import org.axonframework.eventhandling.TrackingEventProcessor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class EventsReplayController {

    private final EventProcessingConfiguration eventProcessingConfiguration;

    public EventsReplayController(EventProcessingConfiguration eventProcessingConfiguration) {
        this.eventProcessingConfiguration = eventProcessingConfiguration;
    }

    @PostMapping("/event-processor/{processorName}/reset")
    public ResponseEntity<String> replayEvents(@PathVariable String processorName) {

        Optional<TrackingEventProcessor> trackingEventProcessor =
                eventProcessingConfiguration.eventProcessor(processorName, TrackingEventProcessor.class);

        if (trackingEventProcessor.isPresent()) {

            TrackingEventProcessor eventProcessor = trackingEventProcessor.get();
            eventProcessor.shutDown();
            eventProcessor.resetTokens();
            eventProcessor.start();

            return ResponseEntity.ok()
                    .body(String.format("the event processor with a name [%s] has been reset", processorName));

        } else {

            return ResponseEntity.badRequest()
                    .body(String.format("the event processor with a name [%s] is not a tracking event processor. " +
                            "only tracking event processor is supported", processorName));
        }
    }
}
