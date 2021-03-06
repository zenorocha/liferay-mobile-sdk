/**
 * Copyright (c) 2000-2014 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

#import "LRBlockCallback.h"

/**
 * @author Bruno Farache
 */
@interface LRSession : NSObject

@property (nonatomic, strong) id<LRCallback> callback;
@property (nonatomic) int connectionTimeout;
@property (nonatomic, strong) NSString *password;
@property (nonatomic, strong) NSString *server;
@property (nonatomic, strong) NSString *username;
@property (nonatomic, strong) NSOperationQueue *queue;

- (id)initWithServer:(NSString *)server;
- (id)initWithServer:(NSString *)server callback:(id<LRCallback>)callback;

- (id)initWithServer:(NSString *)server username:(NSString *)username
	password:(NSString *)password;

- (id)initWithServer:(NSString *)server username:(NSString *)username
	password:(NSString *)password callback:(id<LRCallback>)callback;

- (id)initWithServer:(NSString *)server username:(NSString *)username
	password:(NSString *)password connectionTimeout:(int)connectionTimeout
	callback:(id<LRCallback>)callback;

- (id)initWithServer:(NSString *)server username:(NSString *)username
	password:(NSString *)password connectionTimeout:(int)connectionTimeout
	callback:(id<LRCallback>)callback queue:(NSOperationQueue *)queue;

- (id)initWithSession:(LRSession *)session;

- (NSString *)getAuthHeader;
- (id)invoke:(NSDictionary *)command error:(NSError **)error;
- (void)onSuccess:(LRSuccessBlock)success onFailure:(LRFailureBlock)failure;
- (id)upload:(NSDictionary *)command error:(NSError **)error;

@end