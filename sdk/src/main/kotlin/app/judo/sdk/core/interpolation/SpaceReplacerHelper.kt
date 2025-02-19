package app.judo.sdk.core.interpolation

import app.judo.sdk.core.lang.Interpolator

internal class SpaceReplacerHelper(
    private val delegate: Interpolator.Helper
) : Interpolator.Helper by delegate {
    override fun invoke(data: Any?, argument: String?): String {
        return delegate(
            data = (data as? String)?.toString()?.replace(" ", "T") ?: data,
            argument = argument
        )
    }
}