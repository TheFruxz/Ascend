package dev.fruxz.ascend.annotation

/**
 * Indicates that the annotated API is intended for internal use only.
 * Such APIs may change or be removed in future versions without notice.
 * Use of these APIs is discouraged unless you are aware of the potential risks.
 **/
@MustBeDocumented
@RequiresOptIn("This is an internal API of Ascend and may change or be removed in future versions.")
annotation class InternalUseOnly
