/**
 * Copyright (c) 2009--2012, ImgLib2 developers
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.  Redistributions in binary
 * form must reproduce the above copyright notice, this list of conditions and
 * the following disclaimer in the documentation and/or other materials
 * provided with the distribution.  Neither the name of the imglib project nor
 * the names of its contributors may be used to endorse or promote products
 * derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package net.imglib2.realtransform;

import net.imglib2.Interval;
import net.imglib2.RealInterval;
import net.imglib2.RealRandomAccess;
import net.imglib2.RealRandomAccessible;

/**
 * 
 *
 * @author Stephan Saalfeld <saalfeld@mpi-cbg.de>
 */
public class ConstantAffineRandomAccessible< T, R extends AffineReadable > extends AffineRandomAccessible< T, R >
{
	final protected AffineTransform constantAffine;
	final protected double[][] ds;
	
	/**
	 * {@link RealRandomAccess} that generates its samples from a target
	 * {@link RealRandomAccessible} at coordinates transformed by a
	 * {@link RealTransform}.
	 *
	 * @author Stephan Saalfeld <saalfeld@mpi-cbg.de>
	 */
	public class AffineRandomAccess extends AffineRandomAccessible< T, R >.AffineRandomAccess
	{
		protected AffineRandomAccess()
		{
			super();
		}
		
		@Override
		protected void scaleMove( final double distance, final int d )
		{
			final double[] dd = ds[ d ];
			for ( int ddd = 0; ddd < n; ++ddd )
				move[ ddd ] = distance * dd[ ddd ];
		}
		
		@Override
		public void fwd( final int d )
		{
			++position[ d ];
			targetAccess.move( ds[ d ] );
		}


		@Override
		public AffineRandomAccess copy()
		{
			return new AffineRandomAccess();
		}

		@Override
		public AffineRandomAccess copyRandomAccess()
		{
			return copy();
		}
	}
	
	public ConstantAffineRandomAccessible( final RealRandomAccessible< T > target, final R transform )
	{
		super( target, transform );
		constantAffine = new AffineTransform( target.numDimensions() );
		constantAffine.set( transform );
		ds = new double[ constantAffine.numSourceDimensions() ][];
		for ( int r = 0; r < ds.length; ++r )
		{
			final double[] d = new double[ ds.length ];
			constantAffine.d( r ).localize( d );
			ds[ r ] = d;
		}
	}
	
	@Override
	public AffineRandomAccess randomAccess()
	{
		return new AffineRandomAccess();
	}

	/**
	 * To be overridden for {@link RealTransform} that can estimate the
	 * boundaries of a transferred {@link RealInterval}.
	 */
	@Override
	public AffineRandomAccess randomAccess( final Interval interval )
	{
		return randomAccess();
	}
}
