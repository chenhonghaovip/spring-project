/*
 *  Copyright 1999-2019 Seata.io Group.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.honghao.cloud.basic.common.dto;

/**
 * @author Geng Zhang
 * @since 0.7.0
 */
public class ProtocolConstants {

    /**
     * Message type: insert
     */
    public static final int INSERT = 0;
    /**
     * Message type: UPDATE
     */
    public static final int UPDATE = 1;
    /**
     * Message type: BATCH_INSERT
     */
    public static final int BATCH_INSERT = 2;
    /**
     * Message type: BATCH_UPDATE
     */
    public static final int BATCH_UPDATE = 3;
    /**
     * Message type: COMPLETE
     */
    public static final int COMPLETE = 4;
    /**
     * Message type: QUERY
     */
    public static final int QUERY = 5;

    /**
     * Message type: Heartbeat Request
     */
    public static final int HEART_BEAT = 6;
}
