/*
 * #%L
 * ImgLib2: a general-purpose, multidimensional image processing library.
 * %%
 * Copyright (C) 2009 - 2020 Tobias Pietzsch, Stephan Preibisch, Stephan Saalfeld,
 * John Bogovic, Albert Cardona, Barry DeZonia, Christian Dietz, Jan Funke,
 * Aivar Grislis, Jonathan Hale, Grant Harris, Stefan Helfrich, Mark Hiner,
 * Martin Horn, Steffen Jaensch, Lee Kamentsky, Larry Lindsey, Melissa Linkert,
 * Mark Longair, Brian Northan, Nick Perry, Curtis Rueden, Johannes Schindelin,
 * Jean-Yves Tinevez and Michael Zinsmaier.
 * %%
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * #L%
 */

package net.imglib2.realtransform;

import net.imglib2.RandomAccess;
import net.imglib2.RandomAccessible;
import net.imglib2.RealRandomAccess;
import net.imglib2.RealRandomAccessible;

/**
 * Convenience factory methods for {@link RealRandomAccessible
 * RealRandomAccessibles} transformed in real coordinate space by
 * {@link InvertibleRealTransform InvertibleRealTransforms}.
 *
 * @author Stephan Saalfeld
 */
public class RealViews
{
	/**
	 * See a {@link RealRandomAccessible} as transformed by an
	 * {@link InvertibleRealTransform}. The {@link InvertibleRealTransform} is
	 * interpreted according to the natural understanding that the source is
	 * transformed by it. E.g. a positive translation of dimension <em>x</em>
	 * would shift the source to the right. Therefore, the samples need to be
	 * generated by the inverse of the {@link InvertibleRealTransform}. Here,
	 * the inverse is realized by {@link InverseRealTransform}. That way,
	 * changing the state of the {@link InvertibleRealTransform} will
	 * immediately change the state of any new {@link RealRandomAccess}
	 * generated by the view.
	 *
	 * @param source
	 *            the {@link RealRandomAccessible} to be transformed
	 * @param transformFromSource
	 *            the {@link InvertibleRealTransform} transforming source
	 *            coordinates to coordinates of the returned
	 *            {@link RealRandomAccessible}
	 * @param <T> the type
	 *
	 * @return {@link RealTransformRealRandomAccessible} representing the
	 *         transformed source
	 */
	public static < T > RealTransformRealRandomAccessible< T, InverseRealTransform > transformReal( final RealRandomAccessible< T > source, final InvertibleRealTransform transformFromSource )
	{
		return new RealTransformRealRandomAccessible< >( source, new InverseRealTransform( transformFromSource ) );
	}

	/**
	 * See a {@link RealRandomAccessible} as a {@link RandomAccessible}
	 * transformed by an {@link InvertibleRealTransform}. The
	 * {@link InvertibleRealTransform} is interpreted according to the natural
	 * understanding that the source is transformed by it. E.g. a positive
	 * translation of dimension <em>x</em> would shift the source to the right.
	 * Therefore, the samples need to be generated by the inverse of the
	 * {@link InvertibleRealTransform}. Here, the inverse is realized by
	 * {@link InverseRealTransform}. That way, changing the state of the
	 * {@link InvertibleRealTransform} will immediately change the state of any
	 * new {@link RandomAccess} generated by the view.
	 *
	 * @param source
	 *            the {@link RealRandomAccessible} to be transformed
	 * @param transformFromSource
	 *            the {@link InvertibleRealTransform} transforming source
	 *            coordinates to coordinates of the returned
	 *            {@link RealRandomAccessible}
	 * @param <T> the type
	 *
	 * @return {@link RealTransformRandomAccessible} representing the
	 *         transformed source
	 */
	public static < T > RealTransformRandomAccessible< T, InverseRealTransform > transform( final RealRandomAccessible< T > source, final InvertibleRealTransform transformFromSource )
	{
		return new RealTransformRandomAccessible< >( source, new InverseRealTransform( transformFromSource ) );
	}

	/**
	 * See a {@link RealRandomAccessible} as transformed by an {@link AffineGet}
	 * . The {@link AffineGet} is interpreted according to the natural
	 * understanding that the source is transformed by it. E.g. a positive
	 * translation of dimension <em>x</em> would shift the source to the right.
	 * Therefore, the samples need to be generated by the inverse of the
	 * {@link AffineGet}. Here, the {@link AffineGet} is inverted using it's
	 * {@link AffineGet#inverse()} method that is expected to generate an
	 * inverse that changes with the original transformation accordingly. That
	 * way, changing the state of the {@link AffineGet} will immediately change
	 * the state of any new {@link RealRandomAccess} generated by the view.
	 *
	 * @param source
	 *            the {@link RealRandomAccessible} to be transformed
	 * @param transformFromSource
	 *            the {@link InvertibleRealTransform} transforming source
	 *            coordinates to coordinates of the returned
	 *            {@link RealRandomAccessible}
	 * @param <T> the type
	 *
	 * @return {@link AffineRealRandomAccessible} representing the transformed
	 *         source
	 */
	public static < T > AffineRealRandomAccessible< T, AffineGet > affineReal( final RealRandomAccessible< T > source, final AffineGet transformFromSource )
	{
		return new AffineRealRandomAccessible< >( source, transformFromSource.inverse() );
	}

	/**
	 * See a {@link RealRandomAccessible} as a {@link RandomAccessible}
	 * transformed by an {@link AffineGet}. The {@link AffineGet} is interpreted
	 * according to the natural understanding that the source is transformed by
	 * it. E.g. a positive translation of dimension <em>x</em> would shift the
	 * source to the right. Therefore, the samples need to be generated by the
	 * inverse of the {@link AffineGet}. Here, the {@link AffineGet} is inverted
	 * using it's {@link AffineGet#inverse()} method that is expected to
	 * generate and inverse that changes with the original transformation
	 * accordingly. That way, changing the state of the {@link AffineGet} will
	 * immediately change the state of any new {@link RandomAccess} generated
	 * by the view.
	 *
	 * @param source
	 *            the {@link RealRandomAccessible} to be transformed
	 * @param transformFromSource
	 *            the {@link InvertibleRealTransform} transforming source
	 *            coordinates to coordinates of the returned
	 *            {@link RealRandomAccessible}
	 * @param <T> the type
	 *
	 * @return {@link AffineRandomAccessible} representing the transformed
	 *         source
	 */
	public static < T > AffineRandomAccessible< T, AffineGet > affine( final RealRandomAccessible< T > source, final AffineGet transformFromSource )
	{
		return new AffineRandomAccessible< >( source, transformFromSource.inverse() );
	}

	/**
	 * Add a dimension to a {@link RealRandomAccessible}.  The resulting
	 * {@link RealRandomAccessible} has samples from the original dimensions
	 * continuously stacked along the added dimensions.
	 *
	 * The additional dimension is the last dimension. For example, an XYZ view
	 * is created for an XY source. When accessing an XYZ sample in the view,
	 * the final coordinate is discarded and the source XY sample is accessed.
	 *
	 * @param source
	 *            the source
	 * @param <T> the type
	 * @return the new {@link RealRandomAccessible}
	 */
	public static < T > RealRandomAccessible< T > addDimension( final RealRandomAccessible< T > source )
	{
		return new StackingRealRandomAccessible< >( source, 1 );
	}

	/**
	 * 
	 * Simplifies all {@link RealTransform}s which are wrapped in the source
	 * {@link RealRandomAccessible}.
	 * 
	 * NB: that the resulting {@link RandomAccessible} copies all
	 * {@link RealTransform}s when required. Former references on any
	 * {@link RealTransform}s wrapped in the {@link RealRandomAccessible} are
	 * invalid for the resulting {@link RandomAccessible}.
	 * 
	 * @param source
	 *            {@link RealRandomAccessible} to be simplified
	 * @param <T> the type
	 * @return potentially simplified {@link RandomAccessible}
	 * 
	 */
	public static < T > RandomAccessible< T > simplify( final RealRandomAccessible< T > source )
	{
		return RealViewsSimplifyUtils.simplify( source );
	}

	/**
	 * 
	 * Simplifies all {@link RealTransform}s which are wrapped in the source
	 * {@link RealRandomAccessible}.
	 * 
	 * NB: that the resulting {@link RealRandomAccessible} copies all
	 * {@link RealTransform}s when required. Former references on any
	 * {@link RealTransform}s wrapped in the {@link RealRandomAccessible} are
	 * invalid for the resulting {@link RealRandomAccessible}.
	 * 
	 * @param source
	 *            {@link RealRandomAccessible} to be simplified
	 * @param <T> the type
	 * @return potentially simplified {@link RealRandomAccessible}
	 * 
	 */
	public static < T > RealRandomAccessible< T > simplifyReal( final RealRandomAccessible< T > source )
	{
		return RealViewsSimplifyUtils.simplifyReal( source );
	}
}
