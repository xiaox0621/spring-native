package org.springframework.web.socket;

import org.springframework.core.task.TaskExecutor;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.converter.CompositeMessageConverter;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.broker.AbstractBrokerMessageHandler;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.messaging.simp.user.UserDestinationMessageHandler;
import org.springframework.messaging.simp.user.UserDestinationResolver;
import org.springframework.messaging.support.AbstractSubscribableChannel;
import org.springframework.nativex.extension.NativeImageConfiguration;
import org.springframework.nativex.extension.NativeImageHint;
import org.springframework.nativex.extension.TypeInfo;
import org.springframework.nativex.type.AccessBits;
import org.springframework.web.socket.config.annotation.DelegatingWebSocketConfiguration;
import org.springframework.web.socket.config.annotation.DelegatingWebSocketMessageBrokerConfiguration;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;
import org.springframework.web.socket.server.standard.TomcatRequestUpgradeStrategy;

@NativeImageHint(trigger = DelegatingWebSocketMessageBrokerConfiguration.class, typeInfos = {
		@TypeInfo(types= {
				DelegatingWebSocketMessageBrokerConfiguration.class,
				WebSocketHandlerRegistry.class,
				AbstractSubscribableChannel.class,
				SimpMessagingTemplate.class,
				WebSocketHandler.class,
				WebSocketTransportRegistration.class,
				AbstractBrokerMessageHandler.class,
				TaskExecutor.class,
				ChannelRegistration.class,
				MessageBrokerRegistry.class,
				CompositeMessageConverter.class,
				UserDestinationResolver.class,
				UserDestinationMessageHandler.class,
				MessageHandler.class,
				SimpUserRegistry.class
		}, access = AccessBits.RESOURCE),
		@TypeInfo(types= TomcatRequestUpgradeStrategy.class)
})
@NativeImageHint(trigger = DelegatingWebSocketConfiguration.class, typeInfos = {
		@TypeInfo(types= TomcatRequestUpgradeStrategy.class)
})
public class WebSocketHints implements NativeImageConfiguration {
}
