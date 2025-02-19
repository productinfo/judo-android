/*
 * Copyright (c) 2020-present, Rover Labs, Inc. All rights reserved.
 * You are hereby granted a non-exclusive, worldwide, royalty-free license to use,
 * copy, modify, and distribute this software in source code or binary form for use
 * in connection with the web services and APIs provided by Rover.
 *
 * This copyright notice shall be included in all copies or substantial portions of
 * the software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package app.judo.sdk.core.robots

import app.judo.sdk.api.models.Experience
import app.judo.sdk.core.data.Resource
import kotlinx.coroutines.flow.Flow
import okhttp3.mockwebserver.RecordedRequest

internal class ExperienceRepositoryRobot : AbstractTestRobot() {

    private var nextThrowable: Throwable? = null

    fun retrieveExperience(judoURL: String): Flow<Resource<Experience, Throwable>> {
        return environment.experienceRepository.retrieveExperience(judoURL)
    }

    fun throwOnNextRequest(throwable: Throwable) {
        nextThrowable = throwable
    }

    fun setResponseCodeTo(code: Int) {
        serverDispatcher.code = {
            code
        }
    }

    override fun onRequest(request: RecordedRequest) {
        nextThrowable?.let {
            nextThrowable = null
            throw it
        }
    }

    fun put(experience: Experience, key: String? = null): Experience? {
        return environment.experienceRepository.put(experience, key, emptyList())
    }

    suspend fun retrieveById(key: String): Experience? {
        return environment.experienceRepository.retrieveById(key = key)
    }

}
