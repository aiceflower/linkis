/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.linkis.manager.common.protocol.resource

import org.apache.linkis.common.ServiceInstance
import org.apache.linkis.manager.common.entity.resource.NodeResource
import org.apache.linkis.protocol.RetryableProtocol
import org.apache.linkis.protocol.engine.JobProgressInfo
import org.apache.linkis.protocol.message.RequestProtocol

import java.util

trait ResourceProtocol extends RequestProtocol

case class ResourceUsedProtocol(
    serviceInstance: ServiceInstance,
    engineResource: NodeResource,
    ticketId: String = null
) extends RequestProtocol

case class ResponseTaskYarnResource(
    execId: String,
    resourceMap: util.HashMap[String, ResourceWithStatus]
) extends RequestProtocol

case class ResponseTaskRunningInfo(
    execId: String,
    progress: Float,
    progressInfo: Array[JobProgressInfo],
    resourceMap: util.HashMap[String, ResourceWithStatus],
    extraInfoMap: util.HashMap[String, Object]
) extends RetryableProtocol
    with RequestProtocol {

  private val mutableResourceMap = Option(resourceMap).getOrElse(new util.HashMap)
  private val mutableExtraInfoMap = Option(extraInfoMap).getOrElse(new util.HashMap)

  def getResourceMaps: util.HashMap[String, ResourceWithStatus] = mutableResourceMap

  def getExtraInfoMap: util.HashMap[String, Object] = mutableExtraInfoMap

}
